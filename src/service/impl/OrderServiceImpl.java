package service.impl;

import builder.OrderBuilder;
import builder.OrderBuilderImpl;
import dto.*;
import exception.DatabaseException;
import exception.InvalidOrderException;
import exception.OrderManagementException;
import exception.OutOfStockException;
import model.*;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import service.InventoryService;
import service.NotificationService;
import service.OrderService;
import service.ShippingService;
import state.*;
import validator.OrderValidator;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * OrderServiceImpl (Admin Extension)
 * ----------------------------------------------------------------------------
 * Sequence Diagram steps (Admin Order Management):
 *
 *   === LISTING ===
 *   Controller -> Service: getOrders(filters)
 *     Service -> DB: findAll() + filter by status/date/email
 *   DB --> Service: return orderList
 *   Service --> Controller: return orderList
 *
 *   === APPROVAL FLOW ===
 *   Controller -> Service: processApproval(orderID)
 *     Service -> DB: findById(orderID)
 *     Service -> InventoryService: 8. verifyStock(orderID)
 *       [alt: hết hàng] -> NotificationService: 8b. notifyOutOfStock()
 *     Service -> InventoryService: reserveStock(orderID)
 *     Service: 9. Update state -> ApprovedState
 *     Service -> NotificationService: notifyOrderApproved()
 *   Service --> Controller: return approved Order
 *
 *   === CANCELLATION FLOW ===
 *   Controller -> Service: processCancellation(orderID, reason)
 *     Service -> DB: findById(orderID)
 *     Service: 6d. Check current state (reject if Shipped/Dispatched)
 *     Service: 6e. Update state -> CancelledState
 *     Service -> InventoryService: 6f. releaseStock()
 *     Service -> NotificationService: 6g. notifyOrderCancelled()
 *   Service --> Controller: return cancelled Order
 *
 *   === SHIPPER ASSIGNMENT ===
 *   Controller -> Service: processAssignment(orderID, shipperID)
 *     Service -> ShippingService: assignShipper(orderID, shipperID)
 *       ShippingService -> DB: generateTrackingCode + update order
 *     Service: 12. Create TrackingInfo with tracking code
 *     Service -> NotificationService: 14. notifyOrderDispatched()
 *     Service: 15. Update state -> DispatchedState
 *   Service --> Controller: return TrackingInfo
 * ============================================================================
 */
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private final ShippingService shippingService;
    private final NotificationService notificationService;

    public OrderServiceImpl() {
        this.orderRepository = new OrderRepositoryImpl();
        this.inventoryService = new InventoryServiceImpl();
        this.shippingService = new ShippingServiceImpl();
        this.notificationService = new NotificationServiceImpl();
    }

    /* ===================================================================== */
    /* UC_5: Customer places order (existing)                                */
    /* ===================================================================== */
    @Override
    public Order placeOrder(OrderRequest request)
            throws InvalidOrderException, OutOfStockException, DatabaseException {

        /* BF5.7 Validate order */
        OrderValidator.validate(request);

        /* BF5.8 Check inventory */
        for (CartItem item : request.getCart().getItems()) {
            if (!item.getComicBook().isAvailable(item.getQuantity())) {
                throw new OutOfStockException(
                        item.getComicBook().getTitle() + " is out of stock."
                );
            }
        }

        /* BF5.9 Build order via Builder pattern */
        OrderBuilder builder = new OrderBuilderImpl();
        Order order = builder
                .setCustomer(request.getCustomer())
                .setCart(request.getCart())
                .setShippingAddress(request.getShippingAddress())
                .setPhoneNumber(request.getPhoneNumber())
                .build();

        /* BF5.10 Save order */
        if (!orderRepository.save(order)) {
            throw new DatabaseException("Cannot save order.");
        }

        /* BF5.11 Update inventory */
        for (CartItem item : request.getCart().getItems()) {
            item.getComicBook().decreaseStock(item.getQuantity());
        }

        /* BF5.12 Clear cart */
        request.getCart().clear();
        return order;
    }

    /* ===================================================================== */
    /* ADMIN: Order Management Methods                                       */
    /* ===================================================================== */

    /* Sequence 1-2-3-4: Get filtered order list */
    @Override
    public List<Order> getOrders(Map<String, String> filters) {
        System.out.println("[SERVICE] Getting orders with filters: " + filters);
        List<Order> allOrders = orderRepository.findAll();

        if (filters == null || filters.isEmpty()) {
            return allOrders;
        }

        return allOrders.stream()
                .filter(order -> {
                    boolean match = true;

                    if (filters.containsKey("status")) {
                        String filterStatus = filters.get("status");
                        match = match && order.getState().getStateName().equalsIgnoreCase(filterStatus);
                    }

                    if (filters.containsKey("fromDate") && filters.containsKey("toDate")) {
                        LocalDate from = LocalDate.parse(filters.get("fromDate"));
                        LocalDate to = LocalDate.parse(filters.get("toDate"));
                        LocalDate orderDate = order.getOrderDate().toLocalDate();
                        match = match && !orderDate.isBefore(from) && !orderDate.isAfter(to);
                    }

                    if (filters.containsKey("customerEmail")) {
                        match = match && order.getCustomer() != null
                                && order.getCustomer().getEmail()
                                .equalsIgnoreCase(filters.get("customerEmail"));
                    }

                    return match;
                })
                .collect(Collectors.toList());
    }

    /* Sequence 5-6: Get single order details */
    @Override
    public Order getOrderDetails(int orderID) {
        System.out.println("[SERVICE] Getting order details for #" + orderID);
        return orderRepository.findById(orderID);
    }

    /* Sequence 7 -> 8 -> 8a -> 9: Approve order */
    @Override
    public Order processApproval(int orderID) throws Exception {
        System.out.println("[SERVICE] Processing approval for order #" + orderID);

        Order order = orderRepository.findById(orderID);
        if (order == null) {
            throw new OrderManagementException("Order #" + orderID + " not found.");
        }

        /* Sequence 8: Check inventory via InventoryService */
        StockResult stockResult = inventoryService.verifyStock(orderID);
        if (!stockResult.isAvailable()) {
            /* Sequence 8a-8b: Out of stock -> notify + throw */
            notificationService.notifyOutOfStock(order);
            throw new OutOfStockException("Cannot approve: " + stockResult.getMessage());
        }

        /* Sequence 8a (du hang): Reserve stock */
        inventoryService.reserveStock(orderID);

        /* Sequence 9: Update status to APPROVED */
        order.setState(new ApprovedState());

        /* Sequence 10: Get available shippers (sequence step 24) */
        shippingService.getAvailableShippers();

        return order;
    }

    /* Sequence 6b -> 6c -> 6d -> 6e -> 6f -> 6g: Cancel order */
    @Override
    public Order processCancellation(int orderID, String reason) throws Exception {
        System.out.println("[SERVICE] Processing cancellation for order #" + orderID);

        Order order = orderRepository.findById(orderID);
        if (order == null) {
            throw new OrderManagementException("Order #" + orderID + " not found.");
        }

        /* Sequence 6d: validateOrderForCancellation */
        ValidationResult vResult = validateOrderForCancellation(orderID);
        if (!vResult.isValid()) {
            throw new OrderManagementException(vResult.getErrorMessage());
        }

        /* Sequence 6f (part 1): Release stock back to inventory */
        inventoryService.releaseStock(orderID);
        /* Sequence 6f (part 2): Process refund */
        processRefund(orderID);

        /* Sequence 6e: Update status to CANCELLED */
        order.setState(new CancelledState());
        order.setCancellationReason(reason);

        /* Sequence 6g: Send cancellation notification */
        notificationService.sendCancellationNotification(orderID);

        return order;
    }

    /* Sequence 10c-11a: Auto-assign shipper (demo se goi 12-15 rieng) */
    @Override
    public TrackingInfo processAutoAssignment(int orderID) {
        System.out.println("[SERVICE] Processing auto-assignment for order #" + orderID);
        return shippingService.autoAssignShippingUnit(orderID);
    }

    /* Sequence 10c-11: Manually assign specific shipper (demo se goi 12-15 rieng) */
    @Override
    public TrackingInfo processAssignment(int orderID, long shipperID) {
        System.out.println("[SERVICE] Processing manual assignment for order #" + orderID
                + " to shipper #" + shipperID);
        return shippingService.assignShippingUnit(orderID, shipperID);
    }

    /* Sequence 12b-12c: Update order info when info is insufficient */
    @Override
    public void processUpdateInfo(int orderID, Map<String, String> info) {
        System.out.println("[SERVICE] Updating info for order #" + orderID + ": " + info);
    }

    /* Sequence 6f: Process refund after cancellation */
    @Override
    public void processRefund(int orderID) {
        System.out.println("[SERVICE] Processing refund for order #" + orderID);
        System.out.println("[SERVICE] Refund completed: $" + "XXX has been returned to customer.");
    }

    /* Utility: Update order to any status */
    @Override
    public void updateStatus(int orderID, String status) {
        System.out.println("[SERVICE] Updating order #" + orderID + " status to " + status);
        Order order = orderRepository.findById(orderID);
        if (order == null) {
            System.out.println("[SERVICE] Order not found!");
            return;
        }

        switch (status.toUpperCase()) {
            case "APPROVED":
                order.setState(new ApprovedState());
                break;
            case "DISPATCHED":
                order.setState(new DispatchedState());
                break;
            case "SHIPPED":
                order.setState(new ShippedState());
                break;
            case "CANCELLED":
                order.setState(new CancelledState());
                break;
            default:
                System.out.println("[SERVICE] Unknown status: " + status);
        }
    }

    /* Sequence 12: Generate tracking code for shipping */
    @Override
    public String generateTrackingCode() {
        return shippingService.generateTrackingCode();
    }

    /* Sequence EF4: Regenerate tracking code if duplicate */
    @Override
    public String regenerateTrackingCode() {
        return shippingService.generateTrackingCode();
    }

    /* ================================================================
       DTO-returning methods (from drawio)
       ================================================================ */
    @Override
    public ApprovalResult approveOrder(int orderID) {
        try {
            Order order = processApproval(orderID);
            return new ApprovalResult(orderID, true, "Order approved successfully.", order);
        } catch (OutOfStockException e) {
            return new ApprovalResult(orderID, false, "Out of stock: " + e.getMessage(), null);
        } catch (Exception e) {
            return new ApprovalResult(orderID, false, "Approval failed: " + e.getMessage(), null);
        }
    }

    @Override
    public CancellationResult cancelOrder(int orderID, String reason) {
        try {
            Order order = processCancellation(orderID, reason);
            return new CancellationResult(orderID, true, "Order cancelled successfully.", reason, order);
        } catch (Exception e) {
            return new CancellationResult(orderID, false, "Cancellation failed: " + e.getMessage(), reason, null);
        }
    }

    @Override
    public AssignmentResult assignShipper(int orderID, long shipperID) {
        TrackingInfo info = processAssignment(orderID, shipperID);
        if (info != null) {
            return new AssignmentResult(orderID, true, "Shipper assigned.", info.getShipper(), info);
        }
        return new AssignmentResult(orderID, false, "Shipper not found.", null, null);
    }

    @Override
    public AssignmentResult autoAssignShipper(int orderID) {
        TrackingInfo info = processAutoAssignment(orderID);
        if (info != null) {
            return new AssignmentResult(orderID, true, "Shipper auto-assigned.", info.getShipper(), info);
        }
        return new AssignmentResult(orderID, false, "No available shipper.", null, null);
    }

    @Override
    public RefundResult refundOrder(int orderID) {
        Order order = orderRepository.findById(orderID);
        if (order == null) {
            return new RefundResult(orderID, false, "Order not found.", 0);
        }
        double amount = order.getTotalAmount();
        processRefund(orderID);
        return new RefundResult(orderID, true, "Refund processed.", amount);
    }

    @Override
    public OrderDetails getOrderDetailsDTO(int orderID) {
        Order order = orderRepository.findById(orderID);
        if (order == null) return null;
        return new OrderDetails(order);
    }

    @Override
    public ValidationResult validateOrderForCancellation(int orderID) {
        Order order = orderRepository.findById(orderID);
        if (order == null) {
            return new ValidationResult(false, "Order not found.");
        }
        String state = order.getState().getStateName();
        if ("Shipped".equals(state) || "Dispatched".equals(state)) {
            return new ValidationResult(false,
                    "Cannot cancel order #" + orderID + " because it is already " + state);
        }
        return new ValidationResult(true, "Order can be cancelled.");
    }
}
