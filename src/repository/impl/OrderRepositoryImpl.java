package repository.impl;

import database.FakeDatabase;
import model.Order;
import repository.OrderRepository;

import java.util.List;

/**
 * ============================================================================
 * Class: OrderRepositoryImpl
 * ----------------------------------------------------------------------------
 * Triển khai OrderRepository.
 * ============================================================================
 */
public class OrderRepositoryImpl implements OrderRepository {

    /**
     * Sinh mã đơn hàng.
     */
    private static int nextOrderId = 1;

    @Override
    public boolean save(Order order) {

        order.setOrderId(nextOrderId++);

        return FakeDatabase.ORDERS.add(order);

    }

    @Override
    public Order findById(int orderId) {

        for (Order order : FakeDatabase.ORDERS) {

            if (order.getOrderId() == orderId) {

                return order;

            }

        }

        return null;

    }

    @Override
    public List<Order> findAll() {

        return FakeDatabase.ORDERS;

    }

}