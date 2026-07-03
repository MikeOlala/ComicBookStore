package builder;

import model.Cart;
import model.CartItem;
import model.Customer;
import model.Order;
import model.OrderItem;

/**
 * ============================================================================
 * Builder Pattern
 * ----------------------------------------------------------------------------
 * UC_5 Place Order
 *
 * Basic Flow:
 *      BF5.9 Create Order.
 *
 * Business Rule:
 *      Mỗi CartItem sẽ tạo thành một OrderItem.
 * ============================================================================
 */
public class OrderBuilderImpl implements OrderBuilder {

    /**
     * Order đang được xây dựng.
     */
    private final Order order;

    /**
     * Cart nguồn.
     */
    private Cart cart;

    public OrderBuilderImpl() {

        order = new Order();

    }

    @Override
    public OrderBuilder setCustomer(Customer customer) {

        order.setCustomer(customer);

        return this;

    }

    @Override
    public OrderBuilder setCart(Cart cart) {

        this.cart = cart;

        return this;

    }

    @Override
    public OrderBuilder setShippingAddress(String shippingAddress) {

        order.setShippingAddress(shippingAddress);

        return this;

    }

    @Override
    public OrderBuilder setPhoneNumber(String phoneNumber) {

        order.setPhoneNumber(phoneNumber);

        return this;

    }

    /**
     * =========================================================================
     * Build Order.
     * =========================================================================
     */
    @Override
    public Order build() {

        if (cart != null) {

            for (CartItem item : cart.getItems()) {

                OrderItem orderItem = new OrderItem(
                        item.getComicBook(),
                        item.getQuantity(),
                        item.getComicBook().getSalePrice()
                );

                order.addOrderItem(orderItem);

            }

        }

        order.calculateTotalAmount();

        return order;

    }

}