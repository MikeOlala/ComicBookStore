package validator;

import dto.RegisterRequest;
import exception.ValidationException;

import java.util.regex.Pattern;

/**
 * ============================================================================
 * Class: RegisterValidator
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_1 Register
 *
 * Basic Flow:
 *      Validate registration information.
 *
 * Alternative Flow:
 *      - Missing required information.
 *      - Invalid email format.
 *      - Password and confirm password do not match.
 * ============================================================================
 */
public final class RegisterValidator {

    /**
     * Email pattern.
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            );

    private RegisterValidator() {
    }

    /**
     * Validate registration request.
     */
    public static void validate(RegisterRequest request)
            throws ValidationException {

        if (request == null) {
            throw new ValidationException("Request is null.");
        }

        if (request.getFullName() == null
                || request.getFullName().trim().isEmpty()) {

            throw new ValidationException(
                    "Full name is required."
            );
        }

        if (request.getEmail() == null
                || request.getEmail().trim().isEmpty()) {

            throw new ValidationException(
                    "Email is required."
            );
        }

        if (!EMAIL_PATTERN.matcher(request.getEmail()).matches()) {

            throw new ValidationException(
                    "Invalid email format."
            );
        }

        if (request.getPassword() == null
                || request.getPassword().trim().isEmpty()) {

            throw new ValidationException(
                    "Password is required."
            );
        }

        if (request.getPassword().length() < 6) {

            throw new ValidationException(
                    "Password must contain at least 6 characters."
            );
        }

        if (!request.getPassword()
                .equals(request.getConfirmPassword())) {

            throw new ValidationException(
                    "Confirm password does not match."
            );
        }
    }

}