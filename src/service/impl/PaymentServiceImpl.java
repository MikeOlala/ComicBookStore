package service.impl;

import dto.PaymentRequest;
import exception.DatabaseException;
import exception.PaymentException;
import model.Order;
import model.Payment;
import repository.OrderRepository;
import repository.PaymentRepository;
import repository.impl.OrderRepositoryImpl;
import repository.impl.PaymentRepositoryImpl;
import service.PaymentService;
import validator.PaymentValidator;

/**
 * ============================================================================
 * PaymentServiceImpl
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_6 Payment
 *
 * Sequence Diagram
 *
 * Validate Payment
 *        ↓
 * Find Order
 *        ↓
 * Payment Strategy
 *        ↓
 * Update Order State
 *        ↓
 * Save Payment
 *        ↓
 * Success
 * ============================================================================
 */
public class PaymentServiceImpl implements PaymentService {

    /**
     * Order Repository.
     */
    private final OrderRepository orderRepository;

    /**
     * Payment Repository.
     */
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl() {

        orderRepository = new OrderRepositoryImpl();

        paymentRepository = new PaymentRepositoryImpl();

    }

    @Override
    public Payment pay(PaymentRequest request)
            throws PaymentException,
            DatabaseException {

        // ==========================================================
        // BF6.2 Validate Payment
        // ==========================================================
        PaymentValidator.validate(request);

        // ==========================================================
        // Find Order
        // ==========================================================
        Order order =
                orderRepository.findById(request.getOrderId());

        if (order == null) {

            throw new PaymentException(
                    "Order not found."
            );

        }

        // ==========================================================
        // BF6.4/BF6.5 Execute Strategy
        // ==========================================================
        boolean success =
                request.getPaymentStrategy().pay(order);

        if (!success) {

            throw new PaymentException(
                    "Payment failed."
            );

        }

        // ==========================================================
        // Create Payment
        // ==========================================================
        Payment payment = new Payment();

        payment.setOrder(order);

        payment.setPaymentStrategy(
                request.getPaymentStrategy());

        payment.setPaid(true);

        // ==========================================================
        // BF6.8 Update Order State
        // ==========================================================
        order.nextState();

        // ==========================================================
        // BF6.9 Save Payment
        // ==========================================================
        boolean saved =
                paymentRepository.save(payment);

        if (!saved) {

            throw new DatabaseException(
                    "Cannot save payment."
            );

        }

        // ==========================================================
        // BF6.10 Notify Success
        // ==========================================================
        System.out.println("===================================");

        System.out.println("Payment Successful");

        System.out.println("Order ID : "
                + order.getOrderId());

        System.out.println("Method   : "
                + payment.getPaymentStrategy()
                .getPaymentMethod());

        System.out.println("Amount   : "
                + payment.getAmount());

        System.out.println("Status   : "
                + order.getState().getStateName());

        System.out.println("===================================");

        return payment;

    }

}