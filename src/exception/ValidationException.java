package exception;

/**
 * ============================================================================
 * Class: ValidationException
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_1 Register
 *
 * Exception dùng khi dữ liệu đầu vào không hợp lệ.
 *
 * Alternative Flow:
 *      - Thiếu thông tin
 *      - Email sai định dạng
 *      - Mật khẩu không hợp lệ
 * ============================================================================
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }

}