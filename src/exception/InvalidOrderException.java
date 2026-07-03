package exception;

/**
 * ============================================================================
 * Class: InvalidOrderException
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_5 Place Order
 *
 * Alternative Flow:
 *      A1. Giỏ hàng trống
 *      A2. Thiếu thông tin giao hàng
 * ============================================================================
 */
public class InvalidOrderException extends Exception {

    public InvalidOrderException(String message) {
        super(message);
    }

}