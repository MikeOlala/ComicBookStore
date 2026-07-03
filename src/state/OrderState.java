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

    /**
     * Chuyển sang trạng thái kế tiếp.
     *
     * @param order Order cần cập nhật
     */
    void next(Order order);

    /**
     * Tên trạng thái.
     *
     * @return trạng thái hiện tại
     */
    String getStateName();

}