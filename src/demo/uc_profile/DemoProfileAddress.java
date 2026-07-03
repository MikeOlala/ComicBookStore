package demo.uc_profile;

import controller.RegisterController;
import controller.UserController;
import dto.RegisterRequest;
import model.Address;
import model.Customer;
import view.ProfileUI;

import java.util.Date;
import java.util.List;

public class DemoProfileAddress {

    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println("   DEMO: USER PROFILE AND ADDRESS BOOK MANAGEMENT  ");
            System.out.println("==================================================");

            // 1. Setup registered customer
            RegisterController registerController = new RegisterController();
            RegisterRequest registerRequest = new RegisterRequest(
                    "Tran Van Binh",
                    "binh@gmail.com",
                    "password123",
                    "password123"
            );
            Customer customer = registerController.register(registerRequest);
            customer.setPhone("0987654321");
            customer.setBirthDate(new Date());
            customer.updateProfile();

            UserController userController = new UserController();
            ProfileUI profileUI = new ProfileUI();

            // ========================================================================
            // SCENARIO 1: View profile information
            // ========================================================================
            System.out.println("\n--- [Scenario 1: View Profile] ---");
            Customer retrievedProfile = userController.getProfile(customer.getUserId());
            profileUI.displayProfile(retrievedProfile);

            // Validate profile
            System.out.println("Profile validation result: " + userController.validateProfile(retrievedProfile));

            // ========================================================================
            // SCENARIO 2: Add multiple addresses & check default automatic update
            // ========================================================================
            System.out.println("\n--- [Scenario 2: Add addresses and check default flag behavior] ---");
            
            // Address 1: Default Address
            Address addr1 = new Address(0, "Tran Van Binh", "0987654321", "Ha Noi", "Cau Giay", "Dich Vong", "123 Xuan Thuy", true);
            boolean added1 = userController.addAddress(customer.getUserId(), addr1);
            if (added1) {
                profileUI.showSuccessMessage("Added Address 1 (Default)");
            } else {
                profileUI.showErrorMessage("Failed to add Address 1");
            }

            // Address 2: Non-default Address
            Address addr2 = new Address(0, "Le Thi Mai (Mom)", "0900112233", "Da Nang", "Hai Chau", "Thach Thang", "45 Le Loi", false);
            boolean added2 = userController.addAddress(customer.getUserId(), addr2);
            if (added2) {
                profileUI.showSuccessMessage("Added Address 2 (Non-Default)");
            } else {
                profileUI.showErrorMessage("Failed to add Address 2");
            }

            // Retrieve and display addresses
            profileUI.displayAddressList(userController.getAddresses(customer.getUserId()));
            System.out.println("Customer default address is: " + (customer.getAdress() != null ? customer.getAdress().getDetailAddress() : "None"));

            // Now, set Address 2 as the new Default address
            System.out.println("\nSetting Address 2 as new default...");
            addr2.setDefault();
            
            // Display addresses again to verify addr1 is no longer default
            profileUI.displayAddressList(userController.getAddresses(customer.getUserId()));
            System.out.println("Customer default address is now: " + (customer.getAdress() != null ? customer.getAdress().getDetailAddress() : "None"));

            // ========================================================================
            // SCENARIO 3: Update an address details
            // ========================================================================
            System.out.println("\n--- [Scenario 3: Update Address] ---");
            System.out.println("Updating Address 2 detailAddress to '45 Le Loi, Q.Hai Chau'...");
            addr2.setDetailAddress("45 Le Loi, Q.Hai Chau");
            addr2.updateAddress();
            boolean updated = userController.updateAddress(customer.getUserId(), addr2);
            if (updated) {
                profileUI.showSuccessMessage("Update Address result in DB: " + updated);
            } else {
                profileUI.showErrorMessage("Failed to update Address 2");
            }

            profileUI.displayAddressList(userController.getAddresses(customer.getUserId()));

            // ========================================================================
            // SCENARIO 4: Validate Address (Invalid fields check)
            // ========================================================================
            System.out.println("\n--- [Scenario 4: Address Validation Check] ---");
            Address invalidAddress = new Address(0, "", "", "Ha Noi", "", "", "", false);
            System.out.println("Validating empty address: " + userController.validateAddress(invalidAddress));
            boolean addedInvalid = userController.addAddress(customer.getUserId(), invalidAddress);
            if (addedInvalid) {
                profileUI.showSuccessMessage("Added invalid address");
            } else {
                profileUI.showErrorMessage("Attempt to add invalid address to DB failed (Expected)");
            }

            // ========================================================================
            // SCENARIO 5: Delete an address
            // ========================================================================
            System.out.println("\n--- [Scenario 5: Delete Address] ---");
            System.out.println("Deleting Address 1 (ID=" + addr1.getAddressId() + ")...");
            boolean deleted = userController.deleteAddress(customer.getUserId(), addr1.getAddressId());
            if (deleted) {
                profileUI.showSuccessMessage("Deleted address successfully");
            } else {
                profileUI.showErrorMessage("Failed to delete address");
            }

            profileUI.displayAddressList(userController.getAddresses(customer.getUserId()));

            System.out.println("\n==================================================");
            System.out.println("         DEMO SUCCESSFULLY COMPLETED               ");
            System.out.println("==================================================");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
