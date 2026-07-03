package validator;

import dto.PaymentRequest;
import exception.PaymentException;

/**
 * ============================================================================
 * Class: PaymentValidator
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_6 Payment
 *
 * Basic Flow:
 *      BF6.2 Validate payment information.
 *
 * Alternative Flow:
 *      A3 Invalid payment method.
 * ============================================================================
 */
public final class PaymentValidator {

    private PaymentValidator() {
    }

    /**
     * Validate payment request.
     */
    public static void validate(PaymentRequest request)
            throws PaymentException {

        if (request == null) {

            throw new PaymentException(
                    "Payment request is null."
            );
        }

        if (request.getOrderId() <= 0) {

            throw new PaymentException(
                    "Invalid order ID."
            );
        }

        if (request.getPaymentStrategy() == null) {

            throw new PaymentException(
                    "Payment method is required."
            );
        }
    }

}