package repository;

import model.Customer;

import java.util.List;

/**
 * ============================================================================
 * Class: UserRepository
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_1 Register
 *
 * Chịu trách nhiệm thao tác dữ liệu Customer.
 * ============================================================================
 */
public interface UserRepository {

    /**
     * Lưu Customer.
     *
     * @param customer Customer cần lưu
     * @return true nếu thành công
     */
    boolean save(Customer customer);

    /**
     * Tìm Customer theo email.
     *
     * @param email Email
     * @return Customer hoặc null
     */
    Customer findByEmail(String email);

    /**
     * Lấy toàn bộ Customer.
     *
     * @return danh sách Customer
     */
    List<Customer> findAll();

}