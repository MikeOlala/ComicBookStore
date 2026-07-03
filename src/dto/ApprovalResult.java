package dto;

import model.Order;

public class ApprovalResult {
    private int orderID;
    private boolean success;
    private String message;
    private Order order;

    public ApprovalResult() {}

    public ApprovalResult(int orderID, boolean success, String message, Order order) {
        this.orderID = orderID;
        this.success = success;
        this.message = message;
        this.order = order;
    }

    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
