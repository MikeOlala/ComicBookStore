package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private String phone;
    private Date birthDate;
    private Address adress; // Địa chỉ mặc định
    private List<Address> addresses = new ArrayList<>();

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

    public String viewProfile() {
        return "Customer Profile: ID=" + userId + ", Name=" + fullName + ", Email=" + email 
                + ", Phone=" + phone + ", BirthDate=" + birthDate;
    }

    public void updateProfile() {
        System.out.println("Profile updated for Customer: " + fullName);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}