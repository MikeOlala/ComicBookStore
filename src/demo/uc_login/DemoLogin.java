package demo.uc_login;

import controller.RegisterController;
import controller.LoginController;
import dto.RegisterRequest;
import dto.LoginRequest;
import dto.GoogleLoginRequest;
import model.Customer;
import model.User;
import model.LoginLog;
import database.FakeDatabase;

public class DemoLogin {
    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println("          DEMO USE CASE UC-1.1: LOGIN             ");
            System.out.println("==================================================");

            // Setup a registered customer using original RegisterController
            RegisterController registerController = new RegisterController();
            RegisterRequest registerRequest = new RegisterRequest(
                    "Nguyen Van A",
                    "vana@gmail.com",
                    "123456",
                    "123456"
            );
            Customer customer = registerController.register(registerRequest);
            System.out.println("Customer registered: " + customer.getFullName() + " (" + customer.getEmail() + ")");

            // ========================================================================
            // UC-1.1 Login Security Integration Demo
            // ========================================================================
            System.out.println("\n========== UC-1.1 - SECURE LOGIN & AUDITING ==========");
            LoginController loginController = new LoginController();

            // Scenario A: Correct credential login
            System.out.println("\n[Scenario A] Logging in with correct credentials");
            LoginRequest loginRequestOk = new LoginRequest("vana@gmail.com", "123456");
            User loggedUser = loginController.login(loginRequestOk);
            System.out.println("Login Success! Logged user: " + loggedUser.getFullName());

            // Scenario B: Failed login lockout (5 incorrect attempts)
            System.out.println("\n[Scenario B] Attempting 5 consecutive failed logins to trigger account lockout");
            LoginRequest loginRequestWrong = new LoginRequest("vana@gmail.com", "wrong_password");
            for (int i = 1; i <= 5; i++) {
                try {
                    System.out.print("Failed Attempt #" + i + ": ");
                    loginController.login(loginRequestWrong);
                } catch (Exception ex) {
                    System.out.println("Denied: " + ex.getMessage());
                }
            }

            // Scenario C: Attempting login while locked out
            System.out.println("\n[Scenario C] Attempting login during locked out period");
            try {
                loginController.login(loginRequestOk);
            } catch (Exception ex) {
                System.out.println("Denied (Expected): " + ex.getMessage());
            }

            // Scenario D: Google OAuth login (Alternative flow)
            System.out.println("\n[Scenario D] Logging in with Google Account");
            GoogleLoginRequest googleReq = new GoogleLoginRequest("vana@gmail.com", "MOCK_GOOGLE_OAUTH_TOKEN_VALUE");
            User googleUser = loginController.loginWithGoogle(googleReq);
            System.out.println("Google Auth Success! User: " + googleUser.getFullName());

            // Print authentication audit logs
            System.out.println("\n--- [Login Audit Logs] ---");
            for (LoginLog log : FakeDatabase.LOGIN_LOGS) {
                System.out.println(log);
            }

            System.out.println("\n==================================================");
            System.out.println("      DEMO UC-1.1 COMPLETED SUCCESSFULLY          ");
            System.out.println("==================================================");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
