package dto;

import model.Cart;
import model.Customer;

/**
 * ============================================================================
 * Class: OrderRequest
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_5 Place Order
 *
 * Mô tả:
 *      Dữ liệu xác nhận đặt hàng.
 * ============================================================================
 */
public class OrderRequest {

    /**
     * Người đặt hàng.
     */
    private Customer customer;

    /**
     * Giỏ hàng.
     */
    private Cart cart;

    /**
     * Địa chỉ giao hàng.
     */
    private String shippingAddress;

    /**
     * Số điện thoại.
     */
    private String phoneNumber;

    public OrderRequest() {
    }

    public OrderRequest(Customer customer,
                        Cart cart,
                        String shippingAddress,
                        String phoneNumber) {

        this.customer = customer;
        this.cart = cart;
        this.shippingAddress = shippingAddress;
        this.phoneNumber = phoneNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}