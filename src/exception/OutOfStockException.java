package exception;

/**
 * ============================================================================
 * Class: OutOfStockException
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_5 Place Order
 *
 * Alternative Flow:
 *      A3. Sản phẩm hết hàng.
 * ============================================================================
 */
public class OutOfStockException extends Exception {

    public OutOfStockException(String message) {
        super(message);
    }

}