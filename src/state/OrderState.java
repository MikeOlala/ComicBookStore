package state;

import model.Order;

/**
 * ============================================================================
 * State Pattern
 * ----------------------------------------------------------------------------
 * UC_6 - Payment
 *
 * Mỗi trạng thái của Order sẽ quyết định trạng thái tiếp theo.
 *
 * State Flow:
 *
 * Pending
 *      ↓
 * Processing
 *      ↓
 * Shipped
 * ============================================================================
 */
public interface OrderState {

    void next(Order order);

    void cancel(Order order);

    String getStateName();

}