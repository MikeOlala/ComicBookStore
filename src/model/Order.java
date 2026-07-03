package model;

import state.OrderState;
import state.PendingState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Class: Order
 * ----------------------------------------------------------------------------
 * UC_5 - Place Order
 *
 * Basic Flow:
 *      BF5.9 Tạo đơn hàng.
 *      BF5.10 Lưu đơn hàng.
 *
 * Business Rules:
 *      - Đơn hàng phải có ít nhất một sản phẩm.
 *      - Tổng tiền = Σ (đơn giá × số lượng).
 * ============================================================================
 */
public class Order {

    /**
     * Mã đơn hàng.
     */
    private int orderId;

    /**
     * Khách hàng.
     */
    private Customer customer;

    /**
     * Danh sách sản phẩm.
     */
    private List<OrderItem> orderItems;

    /**
     * Tổng tiền.
     */
    private double totalAmount;

    /**
     * Thời gian tạo đơn.
     */
    private LocalDateTime orderDate;

    /**
     * Địa chỉ giao hàng.
     */
    private String shippingAddress;

    /**
     * Số điện thoại.
     */
    private String phoneNumber;

    /**
     * State Pattern.
     */
    private OrderState state;

    public Order() {

        orderItems = new ArrayList<>();

        orderDate = LocalDateTime.now();

        state = new PendingState();

    }

    /**
     * Thêm sản phẩm.
     */
    public void addOrderItem(OrderItem item) {

        orderItems.add(item);

        calculateTotalAmount();

    }

    /**
     * Tính tổng tiền.
     */
    public void calculateTotalAmount() {

        totalAmount = 0;

        for (OrderItem item : orderItems) {

            totalAmount += item.getSubTotal();

        }

    }

    /**
     * State Pattern.
     */
    public void nextState() {

        state.next(this);

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }
}