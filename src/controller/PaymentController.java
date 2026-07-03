package controller;

import dto.PaymentRequest;
import exception.DatabaseException;
import exception.PaymentException;
import model.Payment;
import service.PaymentService;
import service.impl.PaymentServiceImpl;

/**
 * ============================================================================
 * Controller: PaymentController
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_6 Payment
 * ============================================================================
 */
public class PaymentController {

    /**
     * Payment Service.
     */
    private final PaymentService paymentService;

    public PaymentController() {

        paymentService = new PaymentServiceImpl();

    }

    /**
     * Thanh toán.
     */
    public Payment pay(PaymentRequest request)
            throws PaymentException,
            DatabaseException {

        return paymentService.pay(request);

    }

}