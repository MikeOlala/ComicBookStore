package service.impl;

import database.FakeDatabase;
import dto.LoginRequest;
import dto.GoogleLoginRequest;
import exception.ValidationException;
import model.User;
import model.LoginLog;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import security.PasswordEncryptor;
import service.LoginService;

import java.time.LocalDateTime;

public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private static int logCounter = 1;

    public LoginServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public User login(LoginRequest request) throws ValidationException {
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new ValidationException("Email and password cannot be null.");
        }

        String email = request.getEmail();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            recordLog(email, "CREDENTIALS", "FAILED_USER_NOT_FOUND");
            throw new ValidationException("Tài khoản không tồn tại.");
        }

        LocalDateTime now = LocalDateTime.now();

        if (user.isLockedOut()) {
            recordLog(email, "CREDENTIALS", "LOCKED_OUT");
            throw new ValidationException("Tài khoản đang bị khóa tạm thời. Thử lại sau: " + user.getLockoutUntil());
        }

        String hashedInput = PasswordEncryptor.encrypt(request.getPassword());
        if (user.getPassword().equals(hashedInput)) {
            user.setFailedLoginAttempts(0);
            user.setLockoutUntil(null);
            recordLog(email, "CREDENTIALS", "SUCCESS");
            return user;
        } else {
            int failed = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(failed);

            if (failed >= 5) {
                user.setLockoutUntil(now.plusMinutes(15));
                recordLog(email, "CREDENTIALS", "LOCKED");
                throw new ValidationException("Tài khoản đã bị khóa 15 phút do nhập sai 5 lần.");
            } else {
                recordLog(email, "CREDENTIALS", "FAILED");
                throw new ValidationException("Mật khẩu không hợp lệ (" + failed + "/5).");
            }
        }
    }

    @Override
    public User loginWithGoogle(GoogleLoginRequest request) throws ValidationException {
        if (request == null || request.getEmail() == null || request.getToken() == null || request.getToken().trim().isEmpty()) {
            recordLog(request != null ? request.getEmail() : "unknown", "GOOGLE", "FAILED");
            throw new ValidationException("Google token is invalid.");
        }

        String email = request.getEmail();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            recordLog(email, "GOOGLE", "FAILED_USER_NOT_FOUND");
            throw new ValidationException("Tài khoản Google chưa được đăng ký trong hệ thống.");
        }

        user.setFailedLoginAttempts(0);
        user.setLockoutUntil(null);
        recordLog(email, "GOOGLE", "SUCCESS");
        return user;
    }

    private void recordLog(String email, String method, String status) {
        LoginLog log = new LoginLog(logCounter++, email, method, status, LocalDateTime.now());
        FakeDatabase.LOGIN_LOGS.add(log);
    }
}
