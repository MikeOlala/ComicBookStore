package repository;

import model.Order;

import java.util.List;

/**
 * ============================================================================
 * Class: OrderRepository
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_5 Place Order
 * ============================================================================
 */
public interface OrderRepository {

    /**
     * Lưu đơn hàng.
     */
    boolean save(Order order);

    /**
     * Tìm đơn hàng theo ID.
     */
    Order findById(int orderId);

    /**
     * Lấy tất cả đơn hàng.
     */
    List<Order> findAll();

}