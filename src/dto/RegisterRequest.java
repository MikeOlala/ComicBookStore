package dto;

/**
 * ============================================================================
 * Class: RegisterRequest
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_1 Register
 *
 * Mô tả:
 *      Đối tượng truyền dữ liệu đăng ký từ Controller sang Service.
 *
 * Sequence Diagram:
 *      RegisterController
 *              ↓
 *      RegisterService
 * ============================================================================
 */
public class RegisterRequest {

    /**
     * Họ và tên.
     */
    private String fullName;

    /**
     * Email.
     */
    private String email;

    /**
     * Mật khẩu.
     */
    private String password;

    /**
     * Xác nhận mật khẩu.
     */
    private String confirmPassword;

    public RegisterRequest() {
    }

    public RegisterRequest(String fullName,
                           String email,
                           String password,
                           String confirmPassword) {

        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Mật khẩu gốc.
     * Service sẽ mã hóa trước khi lưu.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}