package service;

import model.Order;

public interface NotificationService {
    void notifyOrderApproved(Order order);
    void notifyOrderCancelled(Order order);
    void notifyOrderDispatched(Order order);
    void notifyOutOfStock(Order order);
    void notifyError(String errorMessage);

    // Drawio methods
    boolean sendNotifications(int orderID);
    boolean sendEmail(String customerEmail, String subject, String body);
    boolean sendPushNotification(int customerID, String message);
    void sendCancellationNotification(int orderID);
}
