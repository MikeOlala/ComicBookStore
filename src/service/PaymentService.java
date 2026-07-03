package service;

import dto.PaymentRequest;
import exception.DatabaseException;
import exception.PaymentException;
import model.Payment;

/**
 * ============================================================================
 * Interface: PaymentService
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_6 Payment
 *
 * Chịu trách nhiệm xử lý nghiệp vụ thanh toán.
 * ============================================================================
 */
public interface PaymentService {

    /**
     * Thanh toán đơn hàng.
     *
     * Basic Flow
     * BF6.2 Validate payment
     * BF6.3 Confirm payment
     * BF6.4/BF6.5 Execute payment strategy
     * BF6.8 Update order status
     * BF6.9 Save payment
     * BF6.10 Notify success
     *
     * @param request PaymentRequest
     * @return Payment
     */
    Payment pay(PaymentRequest request)
            throws PaymentException,
            DatabaseException;

}