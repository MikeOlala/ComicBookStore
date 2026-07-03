package service.impl;

import database.FakeDatabase;
import model.Order;
import model.User;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import service.NotificationService;

/**
 * ============================================================================
 * NotificationServiceImpl
 * ----------------------------------------------------------------------------
 * Sequence Diagram steps:
 *   [OrderService -> NotificationService]  9. notifyOrderApproved()
 *   [OrderService -> NotificationService]  6g. notifyOrderCancelled()
 *   [OrderService -> NotificationService]  14. notifyOrderDispatched()
 *   [OrderService -> NotificationService]  8b. notifyOutOfStock()
 *   [System      -> NotificationService]  EF1-EF4. notifyError()
 * ============================================================================
 */
public class NotificationServiceImpl implements NotificationService {

    private final OrderRepository orderRepository;

    public NotificationServiceImpl() {
        this.orderRepository = new OrderRepositoryImpl();
    }

    /* Sequence 9: Notify customer that order has been approved */
    @Override
    public void notifyOrderApproved(Order order) {
        System.out.println("[NOTIFICATION] Don hang #" + order.getOrderId()
                + " da duoc phe duyet. Khach hang: " + order.getCustomer().getFullName());
    }

    /* Sequence 6g: Notify customer about cancellation + refund */
    @Override
    public void notifyOrderCancelled(Order order) {
        System.out.println("[NOTIFICATION] Don hang #" + order.getOrderId()
                + " da bi huy. Ly do: " + order.getCancellationReason());
        System.out.println("[NOTIFICATION] Tien se duoc hoan lai cho khach hang: "
                + order.getCustomer().getFullName());
    }

    /* Sequence 14: Notify customer that order has been dispatched with tracking */
    @Override
    public void notifyOrderDispatched(Order order) {
        String trackingCode = order.getTrackingInfo() != null
                ? order.getTrackingInfo().getTrackingCode() : "N/A";
        System.out.println("[NOTIFICATION] Don hang #" + order.getOrderId()
                + " da duoc van chuyen. Ma van don: " + trackingCode
                + ". Khach hang: " + order.getCustomer().getFullName());
    }

    /* Sequence 8b: Notify when order cannot be approved due to stock issues */
    @Override
    public void notifyOutOfStock(Order order) {
        System.out.println("[NOTIFICATION] THONG BAO: Don hang #" + order.getOrderId()
                + " khong the duyet vi het hang. Khach hang: "
                + order.getCustomer().getFullName());
    }

    /* EF1-EF4: Notify about system errors */
    @Override
    public void notifyError(String errorMessage) {
        System.out.println("[NOTIFICATION] LOI HE THONG: " + errorMessage);
    }

    /* ================================================================
       Drawio methods
       ================================================================ */
    @Override
    public boolean sendNotifications(int orderID) {
        Order order = orderRepository.findById(orderID);
        if (order == null) {
            System.out.println("[NOTIFICATION] Order #" + orderID + " not found for notification.");
            return false;
        }
        System.out.println("[NOTIFICATION] Sending notifications for order #" + orderID
                + " to customer: " + order.getCustomer().getFullName());
        System.out.println("[NOTIFICATION]   - SMS: Don hang #" + orderID + " da duoc cap nhat.");
        System.out.println("[NOTIFICATION]   - Email: Gui toi " + order.getCustomer().getEmail());
        System.out.println("[NOTIFICATION]   - Push: Thong bao tren ung dung.");
        return true;
    }

    @Override
    public boolean sendEmail(String customerEmail, String subject, String body) {
        System.out.println("[NOTIFICATION] Sending email to " + customerEmail);
        System.out.println("[NOTIFICATION]   Subject: " + subject);
        System.out.println("[NOTIFICATION]   Body: " + body);
        System.out.println("[NOTIFICATION]   Email sent successfully.");
        return true;
    }

    @Override
    public boolean sendPushNotification(int customerID, String message) {
        String name = "User #" + customerID;
        for (User u : FakeDatabase.USERS) {
            if (u.getUserId() == customerID) {
                name = u.getFullName();
                break;
            }
        }
        System.out.println("[NOTIFICATION] Sending push notification to " + name);
        System.out.println("[NOTIFICATION]   Message: " + message);
        System.out.println("[NOTIFICATION]   Push notification sent successfully.");
        return true;
    }

    @Override
    public void sendCancellationNotification(int orderID) {
        Order order = orderRepository.findById(orderID);
        if (order == null) {
            System.out.println("[NOTIFICATION] Order #" + orderID + " not found.");
            return;
        }
        System.out.println("[NOTIFICATION] Cancellation notification for order #" + orderID);
        System.out.println("[NOTIFICATION]   Khach hang: " + order.getCustomer().getFullName());
        System.out.println("[NOTIFICATION]   Ly do: " + order.getCancellationReason());
        System.out.println("[NOTIFICATION]   Thong bao huy don da duoc gui.");
    }
}
