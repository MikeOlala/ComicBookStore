package state;

import model.Order;

public class ApprovedState implements OrderState {

    @Override
    public void next(Order order) {
        order.setState(new DispatchedState());
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
    }

    @Override
    public String getStateName() {
        return "Approved";
    }
}
