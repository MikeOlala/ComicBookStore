package strategy;

import model.Order;

/**
 * ============================================================================
 * VNPay Payment
 * ----------------------------------------------------------------------------
 * UC_6
 *
 * Basic Flow:
 *      BF6.5
 *      BF6.6
 * ============================================================================
 */
public class VNPayPayment implements PaymentStrategy {

    @Override
    public boolean pay(Order order) {

        if (order == null) {

            return false;

        }

        System.out.println("Connecting to VNPay...");

        System.out.println("VNPay Payment Success.");

        return true;

    }

    @Override
    public String getPaymentMethod() {

        return "VNPay";

    }

}