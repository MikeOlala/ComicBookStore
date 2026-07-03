package dto;

import strategy.PaymentStrategy;

/**
 * ============================================================================
 * Class: PaymentRequest
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_6 Payment
 *
 * Mô tả:
 *      Dữ liệu người dùng gửi khi xác nhận thanh toán.
 * ============================================================================
 */
public class PaymentRequest {

    /**
     * Mã đơn hàng.
     */
    private int orderId;

    /**
     * Phương thức thanh toán.
     */
    private PaymentStrategy paymentStrategy;

    public PaymentRequest() {
    }

    public PaymentRequest(int orderId,
                          PaymentStrategy paymentStrategy) {

        this.orderId = orderId;
        this.paymentStrategy = paymentStrategy;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

}