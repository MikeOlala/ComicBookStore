package validator;

import dto.OrderRequest;
import exception.InvalidOrderException;

/**
 * ============================================================================
 * Class: OrderValidator
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_5 Place Order
 *
 * Basic Flow:
 *      BF5.7 Validate order information.
 *
 * Alternative Flow:
 *      A1 Cart is empty.
 *      A2 Missing shipping information.
 * ============================================================================
 */
public final class OrderValidator {

    private OrderValidator() {
    }

    /**
     * Validate order request.
     */
    public static void validate(OrderRequest request)
            throws InvalidOrderException {

        if (request == null) {
            throw new InvalidOrderException(
                    "Order request is null."
            );
        }

        if (request.getCustomer() == null) {
            throw new InvalidOrderException(
                    "Customer is required."
            );
        }

        if (request.getCart() == null) {
            throw new InvalidOrderException(
                    "Cart is required."
            );
        }

        if (request.getCart().isEmpty()) {
            throw new InvalidOrderException(
                    "Cart is empty."
            );
        }

        if (request.getShippingAddress() == null
                || request.getShippingAddress().trim().isEmpty()) {

            throw new InvalidOrderException(
                    "Shipping address is required."
            );
        }

        if (request.getPhoneNumber() == null
                || request.getPhoneNumber().trim().isEmpty()) {

            throw new InvalidOrderException(
                    "Phone number is required."
            );
        }
    }

}