package demo.uc_profile;

import controller.RegisterController;
import controller.UserController;
import dto.RegisterRequest;
import exception.ValidationException;
import model.Customer;

public class DemoProfile {

    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println("        DEMO USE CASE: RETRIEVE & EDIT PROFILE     ");
            System.out.println("==================================================");

            // 1. Setup registered customers
            RegisterController registerController = new RegisterController();
            
            RegisterRequest regA = new RegisterRequest(
                    "Nguyen Van A",
                    "vana@gmail.com",
                    "123456",
                    "123456"
            );
            Customer customerA = registerController.register(regA);
            System.out.println("Registered Customer A: ID=" + customerA.getUserId() + ", Name=" + customerA.getFullName() + ", Email=" + customerA.getEmail());

            RegisterRequest regB = new RegisterRequest(
                    "Nguyen Van B",
                    "vanb@gmail.com",
                    "123456",
                    "123456"
            );
            Customer customerB = registerController.register(regB);
            System.out.println("Registered Customer B: ID=" + customerB.getUserId() + ", Name=" + customerB.getFullName() + ", Email=" + customerB.getEmail());

            UserController userController = new UserController();

            // ========================================================================
            // SCENARIO A: Retrieve Profile (getProfile)
            // ========================================================================
            System.out.println("\n========== SCENARIO A: RETRIEVE PROFILE ==========");
            System.out.println("Retrieving profile for Customer A (ID=" + customerA.getUserId() + ")...");
            Customer profileA = userController.getProfile(customerA.getUserId());
            System.out.println("Result: Name=\"" + profileA.getFullName() + "\", Email=\"" + profileA.getEmail() + "\"");

            // ========================================================================
            // SCENARIO B: Update Profile successfully (Basic Flow)
            // ========================================================================
            System.out.println("\n========== SCENARIO B: UPDATE PROFILE (BASIC FLOW) ==========");
            System.out.println("Updating profile of Customer A to \"Nguyen Van A Updated\" and \"vana_new@gmail.com\"...");
            
            profileA.setFullName("Nguyen Van A Updated");
            profileA.setEmail("vana_new@gmail.com");
            Customer updatedA = userController.updateProfile(profileA);
            
            System.out.println("Success! Updated Profile details:");
            System.out.println(" - ID: " + updatedA.getUserId());
            System.out.println(" - Name: " + updatedA.getFullName());
            System.out.println(" - Email: " + updatedA.getEmail());

            // Verify using getProfile again
            System.out.println("Verifying with getProfile...");
            Customer verifiedA = userController.getProfile(customerA.getUserId());
            System.out.println(" - Name from DB: " + verifiedA.getFullName());
            System.out.println(" - Email from DB: " + verifiedA.getEmail());

            // ========================================================================
            // SCENARIO C: Update Profile with invalid data (Alternative flow E1: Trống/Sai định dạng)
            // ========================================================================
            System.out.println("\n========== SCENARIO C: INVALID DATA (ALTERNATIVE FLOW E1) ==========");
            
            // Empty Name
            System.out.println("[Test C.1] Attempting to update with empty name:");
            try {
                Customer temp = new Customer(profileA.getUserId(), "", "valid_email@gmail.com", "");
                userController.updateProfile(temp);
            } catch (ValidationException ex) {
                System.out.println("Denied (Expected): " + ex.getMessage());
            }

            // Empty Email
            System.out.println("[Test C.2] Attempting to update with empty email:");
            try {
                Customer temp = new Customer(profileA.getUserId(), "Valid Name", "", "");
                userController.updateProfile(temp);
            } catch (ValidationException ex) {
                System.out.println("Denied (Expected): " + ex.getMessage());
            }

            System.out.println("\n==================================================");
            System.out.println("        DEMO PROFILE COMPLETED SUCCESSFULLY        ");
            System.out.println("==================================================");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
