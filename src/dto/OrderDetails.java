package dto;

import model.*;
import state.OrderState;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDetails {
    private int orderID;
    private String customerName;
    private String customerEmail;
    private List<OrderItem> items;
    private double totalAmount;
    private OrderState state;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private String phoneNumber;
    private String cancellationReason;
    private String trackingCode;

    public OrderDetails() {}

    public OrderDetails(Order order) {
        this.orderID = order.getOrderId();
        this.customerName = order.getCustomer() != null ? order.getCustomer().getFullName() : "";
        this.customerEmail = order.getCustomer() != null ? order.getCustomer().getEmail() : "";
        this.items = order.getOrderItems();
        this.totalAmount = order.getTotalAmount();
        this.state = order.getState();
        this.orderDate = order.getOrderDate();
        this.shippingAddress = order.getShippingAddress();
        this.phoneNumber = order.getPhoneNumber();
        this.cancellationReason = order.getCancellationReason();
        this.trackingCode = order.getTrackingInfo() != null ? order.getTrackingInfo().getTrackingCode() : null;
    }

    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public OrderState getState() { return state; }
    public void setState(OrderState state) { this.state = state; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }
    public String getTrackingCode() { return trackingCode; }
    public void setTrackingCode(String trackingCode) { this.trackingCode = trackingCode; }
}
