package model;

import java.time.LocalDateTime;

import strategy.PaymentStrategy;

/**
 * ============================================================================
 * Class: Payment
 * ----------------------------------------------------------------------------
 * UC_6 - Payment
 *
 * Entity lưu thông tin giao dịch thanh toán.
 * Không chứa business logic.
 * ============================================================================
 */
public class Payment {

    /**
     * Mã giao dịch.
     */
    private int paymentId;

    /**
     * Đơn hàng.
     */
    private Order order;

    /**
     * Tổng tiền.
     */
    private double amount;

    /**
     * Thời gian thanh toán.
     */
    private LocalDateTime paymentTime;

    /**
     * Strategy Pattern.
     */
    private PaymentStrategy paymentStrategy;

    /**
     * Trạng thái thanh toán.
     */
    private boolean paid;

    public Payment() {
        paymentTime = LocalDateTime.now();
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (order != null) {
            this.amount = order.getTotalAmount();
        }
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}