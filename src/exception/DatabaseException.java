package exception;

/**
 * ============================================================================
 * Class: DatabaseException
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_1 Register
 *      UC_5 Place Order
 *      UC_6 Payment
 *
 * Exception Flow:
 *      Lỗi khi lưu dữ liệu vào hệ thống.
 * ============================================================================
 */
public class DatabaseException extends Exception {

    public DatabaseException(String message) {
        super(message);
    }

}