package controller;

import dto.LoginRequest;
import dto.GoogleLoginRequest;
import model.User;
import service.LoginService;
import service.impl.LoginServiceImpl;

public class LoginController {

    private final LoginService loginService;

    public LoginController() {
        this.loginService = new LoginServiceImpl();
    }

    public User login(LoginRequest request) throws Exception {
        return loginService.login(request);
    }

    public User loginWithGoogle(GoogleLoginRequest request) throws Exception {
        return loginService.loginWithGoogle(request);
    }
}
