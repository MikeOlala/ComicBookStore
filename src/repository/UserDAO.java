package repository;

import database.FakeDatabase;
import model.Address;
import model.Customer;
import model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Class: UserDAO
 * ----------------------------------------------------------------------------
 * Quản lý dữ liệu người dùng (Customer) và địa chỉ (Address) trực tiếp từ Database.
 * ============================================================================
 */
public class UserDAO {

    public Customer findById(int userId) {
        for (User user : FakeDatabase.USERS) {
            if (user instanceof Customer customer) {
                if (customer.getUserId() == userId) {
                    return customer;
                }
            }
        }
        return null;
    }

    public boolean saveProfile(Customer customer) {
        if (customer == null) {
            return false;
        }
        for (int i = 0; i < FakeDatabase.USERS.size(); i++) {
            if (FakeDatabase.USERS.get(i).getUserId() == customer.getUserId()) {
                FakeDatabase.USERS.set(i, customer);
                return true;
            }
        }
        return FakeDatabase.USERS.add(customer);
    }

    public List<Address> findAddresses(int userId) {
        Customer customer = findById(userId);
        if (customer != null) {
            return customer.getAddresses();
        }
        return new ArrayList<>();
    }

    public boolean saveAddress(int userId, Address address) {
        Customer customer = findById(userId);
        if (customer != null && address != null) {
            address.setCustomer(customer);
            if (address.getAddressId() <= 0) {
                int maxId = 0;
                for (Address addr : customer.getAddresses()) {
                    if (addr.getAddressId() > maxId) {
                        maxId = addr.getAddressId();
                    }
                }
                address.setAddressId(maxId + 1);
            }
            if (address.isDefault()) {
                address.setDefault();
            }
            if (!customer.getAddresses().contains(address)) {
                customer.getAddresses().add(address);
            }
            return true;
        }
        return false;
    }

    public boolean updateAddress(int userId, Address address) {
        Customer customer = findById(userId);
        if (customer != null && address != null) {
            for (int i = 0; i < customer.getAddresses().size(); i++) {
                if (customer.getAddresses().get(i).getAddressId() == address.getAddressId()) {
                    address.setCustomer(customer);
                    if (address.isDefault()) {
                        address.setDefault();
                    } else {
                        customer.getAddresses().set(i, address);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteAddress(int userId, int addressId) {
        Customer customer = findById(userId);
        if (customer != null) {
            return customer.getAddresses().removeIf(addr -> addr.getAddressId() == addressId);
        }
        return false;
    }
}
