package model;

/**
 * ============================================================================
 * Class: User
 * ----------------------------------------------------------------------------
 * Super class của Customer.
 *
 * Use Case:
 * UC_1 Register
 * ============================================================================
 */
public abstract class User {

    /**
     * User ID.
     */
    protected int userId;

    /**
     * Full name.
     */
    protected String fullName;

    /**
     * Email.
     */
    protected String email;

    /**
     * Encrypted password.
     */
    protected String password;

    public User() {
    }

    public User(int userId,
                String fullName,
                String email,
                String password) {

        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
     * Password đã được mã hóa.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}