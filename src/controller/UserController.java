package controller;

import exception.ValidationException;
import model.Address;
import model.Customer;
import repository.UserDAO;

import java.util.List;

/**
 * ============================================================================
 * Controller: UserController
 * ----------------------------------------------------------------------------
 * Điều phối yêu cầu liên quan đến thông tin tài khoản người dùng (Profile)
 * và quản lý Sổ địa chỉ (Address) sử dụng trực tiếp UserDAO.
 * ============================================================================
 */
public class UserController {

    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    /**
     * Lấy thông tin hồ sơ cá nhân.
     */
    public Customer getProfile(int userId) throws ValidationException {
        Customer customer = userDAO.findById(userId);
        if (customer == null) {
            throw new ValidationException("User not found with ID: " + userId);
        }
        return customer;
    }

    /**
     * Cập nhật thông tin hồ sơ cá nhân.
     */
    public Customer updateProfile(Customer customer) throws ValidationException {
        if (!validateProfile(customer)) {
            throw new ValidationException("Validation Error: Invalid profile data.");
        }
        boolean success = userDAO.saveProfile(customer);
        if (!success) {
            throw new ValidationException("Database Error: Failed to update profile.");
        }
        return customer;
    }

    /**
     * Lấy danh sách địa chỉ của Khách hàng.
     */
    public List<Address> getAddresses(int userId) {
        return userDAO.findAddresses(userId);
    }

    /**
     * Thêm địa chỉ mới cho Khách hàng.
     */
    public boolean addAddress(int userId, Address address) {
        if (validateAddress(address)) {
            return userDAO.saveAddress(userId, address);
        }
        return false;
    }

    /**
     * Cập nhật địa chỉ cho Khách hàng.
     */
    public boolean updateAddress(int userId, Address address) {
        if (validateAddress(address)) {
            return userDAO.updateAddress(userId, address);
        }
        return false;
    }

    /**
     * Xóa địa chỉ của Khách hàng.
     */
    public boolean deleteAddress(int userId, int addressId) {
        return userDAO.deleteAddress(userId, addressId);
    }

    /**
     * Kiểm tra tính hợp lệ của Customer profile.
     */
    public boolean validateProfile(Customer customer) {
        if (customer == null) return false;
        if (customer.getFullName() == null || customer.getFullName().trim().isEmpty()) return false;
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) return false;
        return true;
    }

    /**
     * Kiểm tra tính hợp lệ của địa chỉ giao hàng.
     */
    public boolean validateAddress(Address address) {
        if (address == null) return false;
        if (address.getReceiverName() == null || address.getReceiverName().trim().isEmpty()) return false;
        if (address.getPhoneNumber() == null || address.getPhoneNumber().trim().isEmpty()) return false;
        if (address.getProvince() == null || address.getProvince().trim().isEmpty()) return false;
        if (address.getDistrict() == null || address.getDistrict().trim().isEmpty()) return false;
        if (address.getWard() == null || address.getWard().trim().isEmpty()) return false;
        if (address.getDetailAddress() == null || address.getDetailAddress().trim().isEmpty()) return false;
        return true;
    }

}
