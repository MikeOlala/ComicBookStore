package service.impl;

import builder.OrderBuilder;
import builder.OrderBuilderImpl;
import dto.OrderRequest;
import exception.DatabaseException;
import exception.InvalidOrderException;
import exception.OutOfStockException;
import model.CartItem;
import model.Order;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import service.OrderService;
import validator.OrderValidator;

/**
 * ============================================================================
 * OrderServiceImpl
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_5 Place Order
 *
 * Sequence:
 *
 * Validate
 *      ↓
 * Check Inventory
 *      ↓
 * Build Order
 *      ↓
 * Save Order
 *      ↓
 * Update Inventory
 *      ↓
 * Success
 * ============================================================================
 */
public class OrderServiceImpl implements OrderService {

    /**
     * Repository.
     */
    private final OrderRepository orderRepository;

    public OrderServiceImpl() {

        orderRepository = new OrderRepositoryImpl();

    }

    @Override
    public Order placeOrder(OrderRequest request)
            throws InvalidOrderException,
            OutOfStockException,
            DatabaseException {

        // ==========================================================
        // BF5.7 Validate Order
        // ==========================================================
        OrderValidator.validate(request);

        // ==========================================================
        // BF5.8 Check Inventory
        // ==========================================================
        for (CartItem item : request.getCart().getItems()) {

            if (!item.getComicBook().isAvailable(item.getQuantity())) {

                throw new OutOfStockException(
                        item.getComicBook().getTitle()
                                + " is out of stock."
                );

            }

        }

        // ==========================================================
        // BF5.9 Build Order
        // ==========================================================
        OrderBuilder builder = new OrderBuilderImpl();

        Order order = builder
                .setCustomer(request.getCustomer())
                .setCart(request.getCart())
                .setShippingAddress(request.getShippingAddress())
                .setPhoneNumber(request.getPhoneNumber())
                .build();

        // ==========================================================
        // BF5.10 Save Order
        // ==========================================================
        boolean success = orderRepository.save(order);

        if (!success) {

            throw new DatabaseException(
                    "Cannot save order."
            );

        }

        // ==========================================================
        // BF5.11 Update Inventory
        // ==========================================================
        for (CartItem item : request.getCart().getItems()) {

            item.getComicBook()
                    .decreaseStock(item.getQuantity());

        }

        // ==========================================================
        // BF5.12 Clear Cart
        // ==========================================================
        request.getCart().clear();

        return order;

    }


}