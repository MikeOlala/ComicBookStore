package strategy;

import model.Order;

/**
 * ============================================================================
 * Strategy Pattern
 * ----------------------------------------------------------------------------
 * UC_6 - Payment
 *
 * Mỗi phương thức thanh toán sẽ triển khai interface này.
 *
 * PaymentService chỉ làm việc với PaymentStrategy,
 * không phụ thuộc vào phương thức thanh toán cụ thể.
 * ============================================================================
 */
public interface PaymentStrategy {

    /**
     * Thực hiện thanh toán.
     *
     * @param order Đơn hàng cần thanh toán
     * @return true nếu thanh toán thành công
     */
    boolean pay(Order order);

    /**
     * Tên phương thức thanh toán.
     *
     * @return tên phương thức
     */
    String getPaymentMethod();

}