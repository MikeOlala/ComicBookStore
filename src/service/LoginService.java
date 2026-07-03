package service;

import dto.LoginRequest;
import dto.GoogleLoginRequest;
import model.User;
import exception.ValidationException;

public interface LoginService {
    User login(LoginRequest request) throws ValidationException;
    User loginWithGoogle(GoogleLoginRequest request) throws ValidationException;
}
