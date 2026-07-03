package model;

public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(int userId, String fullName, String email, String password) {
        super(userId, fullName, email, password);
    }
}
