package model;

/**
 * ============================================================================
 * Class: Customer
 * ----------------------------------------------------------------------------
 * Customer kế thừa User.
 *
 * Theo Class Diagram:
 *
 * Customer ---- 1 Cart
 *
 * ============================================================================
 */
public class Customer extends User {

    /**
     * Cart của khách hàng.
     */
    private Cart cart;

    public Customer() {
    }

    public Customer(int userId,
                    String fullName,
                    String email,
                    String password) {

        super(userId,
                fullName,
                email,
                password);

    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}