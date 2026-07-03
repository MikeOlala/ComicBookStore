package service.impl;

import dto.RegisterRequest;
import exception.DatabaseException;
import exception.DuplicateEmailException;
import exception.ValidationException;
import model.Cart;
import model.Customer;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import security.PasswordEncryptor;
import service.RegisterService;
import validator.RegisterValidator;

/**
 * ============================================================================
 * RegisterServiceImpl
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_1 Register
 *
 * Sequence:
 *
 * Validate
 *      ↓
 * Check Email
 *      ↓
 * Encrypt Password
 *      ↓
 * Create Customer
 *      ↓
 * Save Repository
 * ============================================================================
 */
public class RegisterServiceImpl implements RegisterService {

    /**
     * Repository.
     */
    private final UserRepository userRepository;

    public RegisterServiceImpl() {

        userRepository = new UserRepositoryImpl();

    }

    @Override
    public Customer register(RegisterRequest request)
            throws ValidationException,
            DuplicateEmailException,
            DatabaseException {

        // ==========================================================
        // BF1.1 Validate dữ liệu
        // ==========================================================
        RegisterValidator.validate(request);

        // ==========================================================
        // BF1.2 Kiểm tra email đã tồn tại
        // ==========================================================
        if (userRepository.findByEmail(request.getEmail()) != null) {

            throw new DuplicateEmailException(
                    "Email already exists."
            );

        }

        // ==========================================================
        // BF1.3 Mã hóa mật khẩu
        // ==========================================================
        String encryptedPassword =
                PasswordEncryptor.encrypt(
                        request.getPassword()
                );

        // ==========================================================
        // BF1.4 Tạo Customer
        // ==========================================================
        Customer customer = new Customer();

        customer.setFullName(
                request.getFullName());

        customer.setEmail(
                request.getEmail());

        customer.setPassword(
                encryptedPassword);

        customer.setCart(
                new Cart());

        // ==========================================================
        // BF1.5 Lưu Database
        // ==========================================================
        boolean success =
                userRepository.save(customer);

        if (!success) {

            throw new DatabaseException(
                    "Cannot save customer."
            );

        }

        return customer;

    }

}