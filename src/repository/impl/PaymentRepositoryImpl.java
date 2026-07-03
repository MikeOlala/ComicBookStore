package repository.impl;

import database.FakeDatabase;
import model.Payment;
import repository.PaymentRepository;

import java.util.List;

/**
 * ============================================================================
 * Class: PaymentRepositoryImpl
 * ----------------------------------------------------------------------------
 * Triển khai PaymentRepository.
 * ============================================================================
 */
public class PaymentRepositoryImpl implements PaymentRepository {

    /**
     * Sinh mã giao dịch.
     */
    private static int nextPaymentId = 1;

    @Override
    public boolean save(Payment payment) {

        payment.setPaymentId(nextPaymentId++);

        return FakeDatabase.PAYMENTS.add(payment);

    }

    @Override
    public Payment findById(int paymentId) {

        for (Payment payment : FakeDatabase.PAYMENTS) {

            if (payment.getPaymentId() == paymentId) {

                return payment;

            }

        }

        return null;

    }

    @Override
    public List<Payment> findAll() {

        return FakeDatabase.PAYMENTS;

    }

}