package state;

import model.Order;

public class CancelledState implements OrderState {

    @Override
    public void next(Order order) {
    }

    @Override
    public void cancel(Order order) {
    }

    @Override
    public String getStateName() {
        return "Cancelled";
    }
}
