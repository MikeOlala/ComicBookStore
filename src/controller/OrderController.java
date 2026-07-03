package controller;

import dto.*;
import exception.DatabaseException;
import exception.InvalidOrderException;
import exception.OutOfStockException;
import model.*;
import service.OrderService;
import service.impl.OrderServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 * OrderController (Admin Extension)
 * ----------------------------------------------------------------------------
 * Sequence Diagram steps:
 *   [UI -> Controller]  getOrderList(filters)        -- 1. Truy cập QL đơn hàng
 *   [UI -> Controller]  getOrderDetails(orderID)     -- 5. Chọn đơn hàng
 *   [UI -> Controller]  approveOrder(orderID)        -- 7. Phê duyệt
 *   [UI -> Controller]  cancelOrder(orderID, reason) -- 6b. Hủy đơn
 *   [UI -> Controller]  assignShipper(...)            -- 11. Chọn VC
 *   [UI -> Controller]  autoAssignShipper(orderID)    -- 11a. Tự động gán VC
 * ============================================================================
 */
public class OrderController {

    private final OrderService orderService;

    public OrderController() {
        this.orderService = new OrderServiceImpl();
    }

    /* UC_5: Customer places order */
    public Order placeOrder(OrderRequest request)
            throws InvalidOrderException, OutOfStockException, DatabaseException {
        return orderService.placeOrder(request);
    }

    /* Sequence 1-2: Admin -> getOrderList -> Hiển thị danh sách đơn hàng */
    public List<Order> getOrderList(Map<String, String> filters) {
        System.out.println("[CONTROLLER] Getting order list...");
        return orderService.getOrders(filters);
    }

    /* Sequence 5-6: Admin -> getOrderDetails -> Hiển thị chi tiết đơn hàng */
    public Order getOrderDetails(int orderID) {
        System.out.println("[CONTROLLER] Getting order details for #" + orderID + "...");
        return orderService.getOrderDetails(orderID);
    }

    /* Sequence 7 -> 8 -> 9: Admin phê duyệt -> Kiểm tra tồn kho -> APPROVED */
    public Order approveOrder(int orderID) throws Exception {
        System.out.println("[CONTROLLER] Approving order #" + orderID + "...");
        return orderService.processApproval(orderID);
    }

    /* Sequence 10c-11: Admin chọn đơn vị vận chuyển */
    public TrackingInfo assignShipper(int orderID, long shipperID) {
        System.out.println("[CONTROLLER] Assigning shipper #" + shipperID + " to order #" + orderID + "...");
        return orderService.processAssignment(orderID, shipperID);
    }

    /* Sequence 10c-11a: Hệ thống tự động gán đơn vị vận chuyển */
    public TrackingInfo autoAssignShipper(int orderID) {
        System.out.println("[CONTROLLER] Auto-assigning shipper for order #" + orderID + "...");
        return orderService.processAutoAssignment(orderID);
    }

    /* Sequence 6b-6c: Admin hủy đơn -> Nhập lý do */
    public Order cancelOrder(int orderID, String reason) throws Exception {
        System.out.println("[CONTROLLER] Cancelling order #" + orderID + "...");
        return orderService.processCancellation(orderID, reason);
    }

    /* Sequence 12b-12c: Bổ sung thông tin đơn hàng */
    public void updateOrderInfo(int orderID, Map<String, String> info) {
        System.out.println("[CONTROLLER] Updating order #" + orderID + " info...");
        orderService.processUpdateInfo(orderID, info);
    }
}
