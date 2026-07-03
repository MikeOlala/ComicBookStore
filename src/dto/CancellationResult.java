package dto;

import model.Order;

public class CancellationResult {
    private int orderID;
    private boolean success;
    private String message;
    private String reason;
    private Order order;

    public CancellationResult() {}

    public CancellationResult(int orderID, boolean success, String message, String reason, Order order) {
        this.orderID = orderID;
        this.success = success;
        this.message = message;
        this.reason = reason;
        this.order = order;
    }

    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
