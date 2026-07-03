package service;

import dto.RegisterRequest;
import exception.DatabaseException;
import exception.DuplicateEmailException;
import exception.ValidationException;
import model.Customer;

/**
 * ============================================================================
 * Interface: RegisterService
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_1 Register
 *
 * Mô tả:
 *      Xử lý nghiệp vụ đăng ký tài khoản.
 * ============================================================================
 */
public interface RegisterService {

    /**
     * Đăng ký tài khoản mới.
     *
     * Basic Flow
     * BF1.1 Validate dữ liệu
     * BF1.2 Kiểm tra email
     * BF1.3 Mã hóa mật khẩu
     * BF1.4 Tạo Customer
     * BF1.5 Lưu Database
     *
     * @param request RegisterRequest
     * @return Customer vừa tạo
     */
    Customer register(RegisterRequest request)
            throws ValidationException,
            DuplicateEmailException,
            DatabaseException;

}