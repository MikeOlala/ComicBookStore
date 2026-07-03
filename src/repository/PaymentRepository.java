package repository;

import model.Payment;

import java.util.List;

/**
 * ============================================================================
 * Class: PaymentRepository
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_6 Payment
 * ============================================================================
 */
public interface PaymentRepository {

    /**
     * Lưu giao dịch thanh toán.
     */
    boolean save(Payment payment);

    /**
     * Tìm giao dịch theo ID.
     */
    Payment findById(int paymentId);

    /**
     * Lấy toàn bộ giao dịch.
     */
    List<Payment> findAll();

}