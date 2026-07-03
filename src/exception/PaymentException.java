package exception;

/**
 * ============================================================================
 * Class: PaymentException
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_6 Payment
 *
 * Alternative Flow:
 *      A1. Người dùng hủy thanh toán.
 *      A2. Thanh toán thất bại.
 *      A3. Phương thức thanh toán không hợp lệ.
 *
 * Exception Flow:
 *      Không thể kết nối cổng thanh toán.
 * ============================================================================
 */
public class PaymentException extends Exception {

    public PaymentException(String message) {
        super(message);
    }

}