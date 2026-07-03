package exception;

/**
 * ============================================================================
 * Class: DuplicateEmailException
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_1 Register
 *
 * Alternative Flow:
 *      Email đã tồn tại trong hệ thống.
 * ============================================================================
 */
public class DuplicateEmailException extends Exception {

    public DuplicateEmailException(String message) {
        super(message);
    }

}