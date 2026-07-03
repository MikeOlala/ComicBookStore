package builder;

import model.Cart;
import model.Customer;
import model.Order;

/**
 * ============================================================================
 * Builder Pattern
 * ----------------------------------------------------------------------------
 * UC_5 - Place Order
 *
 * Dùng để xây dựng đối tượng Order.
 * ============================================================================
 */
public interface OrderBuilder {

    /**
     * Thiết lập khách hàng.
     *
     * @param customer Customer
     * @return Builder
     */
    OrderBuilder setCustomer(Customer customer);

    /**
     * Thiết lập giỏ hàng.
     *
     * @param cart Cart
     * @return Builder
     */
    OrderBuilder setCart(Cart cart);

    /**
     * Thiết lập địa chỉ giao hàng.
     *
     * @param shippingAddress Địa chỉ
     * @return Builder
     */
    OrderBuilder setShippingAddress(String shippingAddress);

    /**
     * Thiết lập số điện thoại.
     *
     * @param phoneNumber Số điện thoại
     * @return Builder
     */
    OrderBuilder setPhoneNumber(String phoneNumber);

    /**
     * Tạo Order.
     *
     * @return Order
     */
    Order build();

}