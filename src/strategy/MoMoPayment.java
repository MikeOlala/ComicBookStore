package strategy;

import model.Order;

/**
 * ============================================================================
 * MoMo Payment
 * ----------------------------------------------------------------------------
 * UC_6
 *
 * Basic Flow:
 *      BF6.5
 *      BF6.6
 * ============================================================================
 */
public class MoMoPayment implements PaymentStrategy {

    @Override
    public boolean pay(Order order) {

        if (order == null) {

            return false;

        }

        System.out.println("Connecting to MoMo...");

        System.out.println("MoMo Payment Success.");

        return true;

    }

    @Override
    public String getPaymentMethod() {

        return "MoMo";

    }

}