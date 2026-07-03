package state;

import model.Order;

/**
 * ============================================================================
 * Processing State
 * ----------------------------------------------------------------------------
 * Đơn hàng đang được xử lý.
 *
 * Sau khi giao hàng
 * sẽ chuyển sang Shipped.
 * ============================================================================
 */
public class ProcessingState implements OrderState {

    @Override
    public void next(Order order) {

        order.setState(new ShippedState());

    }

    @Override
    public String getStateName() {

        return "Processing";

    }

}