package dto;

public class RefundResult {
    private int orderID;
    private boolean success;
    private String message;
    private double amount;

    public RefundResult() {}

    public RefundResult(int orderID, boolean success, String message, double amount) {
        this.orderID = orderID;
        this.success = success;
        this.message = message;
        this.amount = amount;
    }

    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
