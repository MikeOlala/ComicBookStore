package controller;

import dto.OrderRequest;
import exception.DatabaseException;
import exception.InvalidOrderException;
import exception.OutOfStockException;
import model.Order;
import service.OrderService;
import service.impl.OrderServiceImpl;

/**
 * ============================================================================
 * Controller: OrderController
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_5 Place Order
 * ============================================================================
 */
public class OrderController {

    /**
     * Order Service.
     */
    private final OrderService orderService;

    public OrderController() {

        orderService = new OrderServiceImpl();

    }

    /**
     * Đặt hàng.
     */
    public Order placeOrder(OrderRequest request)
            throws InvalidOrderException,
            OutOfStockException,
            DatabaseException {

        return orderService.placeOrder(request);

    }

}