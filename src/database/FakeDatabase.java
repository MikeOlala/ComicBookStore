package database;

import model.Order;
import model.Payment;
import model.User;
import model.LoginLog;
import model.Story;
import model.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Class: FakeDatabase
 * ----------------------------------------------------------------------------
 * Mô phỏng cơ sở dữ liệu trong bộ nhớ.
 * ============================================================================
 */
public final class FakeDatabase {

    public static final List<User> USERS = new ArrayList<>();
    public static final List<Order> ORDERS = new ArrayList<>();
    public static final List<Payment> PAYMENTS = new ArrayList<>();
    public static final List<LoginLog> LOGIN_LOGS = new ArrayList<>();
    public static final List<Favorite> FAVORITES = new ArrayList<>();
    public static final List<Story> STORIES = new ArrayList<>();

    private FakeDatabase() {
        // Không cho phép khởi tạo.
    }
}