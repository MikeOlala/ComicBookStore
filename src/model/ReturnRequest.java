package model;

import java.time.LocalDateTime;

public class ReturnRequest {

    private int returnId;
    private Order order;
    private String reason;
    private String status;
    private LocalDateTime createdAt;

    public ReturnRequest() {
        this.createdAt = LocalDateTime.now();
        this.status = "CHO_XU_LY";
    }

    public ReturnRequest(int returnId, Order order, String reason) {
        this.returnId = returnId;
        this.order = order;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
        this.status = "CHO_XU_LY";
    }

    public int getReturnId() { return returnId; }
    public void setReturnId(int returnId) { this.returnId = returnId; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}