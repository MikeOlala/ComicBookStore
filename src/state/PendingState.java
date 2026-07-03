package state;

import model.Order;

/**
 * ============================================================================
 * Pending State
 * ----------------------------------------------------------------------------
 * Đơn hàng vừa được tạo.
 *
 * Sau khi thanh toán thành công
 * sẽ chuyển sang Processing.
 * ============================================================================
 */
public class PendingState implements OrderState {

    @Override
    public void next(Order order) {
        order.setState(new ProcessingState());
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
    }

    @Override
    public String getStateName() {
        return "Pending";
    }

}