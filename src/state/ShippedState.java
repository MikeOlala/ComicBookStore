package state;

import model.Order;

/**
 * ============================================================================
 * Shipped State
 * ----------------------------------------------------------------------------
 * Đơn hàng đã giao thành công.
 *
 * Đây là trạng thái cuối.
 * ============================================================================
 */
public class ShippedState implements OrderState {

    @Override
    public void next(Order order) {
    }

    @Override
    public void cancel(Order order) {
    }

    @Override
    public String getStateName() {
        return "Shipped";
    }

}