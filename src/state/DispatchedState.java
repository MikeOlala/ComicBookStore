package state;

import model.Order;

public class DispatchedState implements OrderState {

    @Override
    public void next(Order order) {
        order.setState(new ShippedState());
    }

    @Override
    public void cancel(Order order) {
    }

    @Override
    public String getStateName() {
        return "Dispatched";
    }
}
