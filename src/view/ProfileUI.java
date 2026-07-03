package view;

import model.Address;
import model.Customer;
import java.util.List;

/**
 * ============================================================================
 * Class: ProfileUI
 * ----------------------------------------------------------------------------
 * Giao diện hiển thị thông tin hồ sơ và địa chỉ của Khách hàng.
 * ============================================================================
 */
public class ProfileUI {

    public void displayProfile(Customer customer) {
        if (customer != null) {
            System.out.println("[UI - Display Profile] " + customer.viewProfile());
        }
    }

    public void displayAddressList(List<Address> addresses) {
        System.out.println("[UI - Display Address List]");
        if (addresses == null || addresses.isEmpty()) {
            System.out.println("   No addresses registered.");
            return;
        }
        for (Address addr : addresses) {
            System.out.println("   - [ID=" + addr.getAddressId() + "] " 
                    + addr.getReceiverName() + " (" + addr.getPhoneNumber() + ") - "
                    + addr.getDetailAddress() + ", " + addr.getWard() + ", " 
                    + addr.getDistrict() + ", " + addr.getProvince()
                    + " [Default=" + addr.isDefault() + "]");
        }
    }

    public void showSuccessMessage(String message) {
        System.out.println("[UI SUCCESS] " + message);
    }

    public void showErrorMessage(String message) {
        System.out.println("[UI ERROR] " + message);
    }
}
