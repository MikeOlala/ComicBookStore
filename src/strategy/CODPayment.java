package strategy;

import model.Order;

/**
 * ============================================================================
 * COD Payment
 * ----------------------------------------------------------------------------
 * UC_6
 *
 * Business Rule:
 *      COD chỉ áp dụng cho đơn hàng hợp lệ.
 *
 * Basic Flow:
 *      BF6.4
 * ============================================================================
 */
public class CODPayment implements PaymentStrategy {

    @Override
    public boolean pay(Order order) {

        if (order == null) {

            return false;

        }

        System.out.println("Cash On Delivery selected.");

        return true;

    }

    @Override
    public String getPaymentMethod() {

        return "Cash On Delivery";

    }

}