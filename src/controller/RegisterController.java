package controller;

import dto.RegisterRequest;
import exception.DatabaseException;
import exception.DuplicateEmailException;
import exception.ValidationException;
import model.Customer;
import service.RegisterService;
import service.impl.RegisterServiceImpl;

/**
 * ============================================================================
 * Controller: RegisterController
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_1 Register
 *
 * Chức năng:
 *      Điều phối yêu cầu đăng ký tài khoản.
 * ============================================================================
 */
public class RegisterController {

    /**
     * Register Service.
     */
    private final RegisterService registerService;

    public RegisterController() {

        registerService = new RegisterServiceImpl();

    }

    /**
     * Xử lý đăng ký.
     */
    public Customer register(RegisterRequest request)
            throws ValidationException,
            DuplicateEmailException,
            DatabaseException {

        return registerService.register(request);

    }

}