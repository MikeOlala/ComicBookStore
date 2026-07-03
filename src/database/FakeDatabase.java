package database;

import model.Order;
import model.Payment;
import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Class: FakeDatabase
 * ----------------------------------------------------------------------------
 * Mô phỏng cơ sở dữ liệu trong bộ nhớ.
 *
 * Dùng cho:
 *      UC_1 Register
 *      UC_5 Place Order
 *      UC_6 Payment
 * ============================================================================
 */
public final class FakeDatabase {

    /**
     * Danh sách người dùng.
     */
    public static final List<User> USERS = new ArrayList<>();

    /**
     * Danh sách đơn hàng.
     */
    public static final List<Order> ORDERS = new ArrayList<>();

    /**
     * Danh sách thanh toán.
     */
    public static final List<Payment> PAYMENTS = new ArrayList<>();

    private FakeDatabase() {
        // Không cho phép khởi tạo.
    }
}