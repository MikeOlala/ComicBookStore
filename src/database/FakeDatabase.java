package database;

import model.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.Payment;
import model.User;
import model.LoginLog;
import model.Favorite;
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

    public static final List<TrendData> TREND_DATA_LIST = new ArrayList<>();
    public static final List<AnalysisResult> ANALYSIS_RESULTS = new ArrayList<>();
    public static final List<ReportData> REPORT_DATA_LIST = new ArrayList<>();
    public static final List<Shipper> SHIPPERS = new ArrayList<>();
    public static final List<TrackingInfo> TRACKING_INFO_LIST = new ArrayList<>();

    public static final List<ComicBook> COMIC_BOOKS = new ArrayList<>();

    static {
        populateTrendData();
        populateShippers();
        populateComicBooks();
        populateUsersAndOrders();
    }

    private static void populateTrendData() {
        // Weekly trend data for "Action" — enough to pass min-10 validation
        TREND_DATA_LIST.add(new TrendData(1, "Action", LocalDate.of(2026, 1, 5), 280, 32, 9600, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(2, "Action", LocalDate.of(2026, 1, 12), 310, 38, 11400, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(3, "Action", LocalDate.of(2026, 1, 19), 295, 36, 10800, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(4, "Action", LocalDate.of(2026, 1, 26), 315, 44, 13200, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(5, "Action", LocalDate.of(2026, 2, 2), 330, 46, 13800, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(6, "Action", LocalDate.of(2026, 2, 9), 340, 48, 14400, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(7, "Action", LocalDate.of(2026, 2, 16), 350, 50, 15000, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(8, "Action", LocalDate.of(2026, 2, 23), 330, 46, 13800, "WEEKLY"));
        // Monthly trend data for "Action" — aggregates
        TREND_DATA_LIST.add(new TrendData(9, "Action", LocalDate.of(2026, 3, 15), 1100, 130, 39000, "MONTHLY"));
        TREND_DATA_LIST.add(new TrendData(10, "Action", LocalDate.of(2026, 4, 15), 1400, 200, 60000, "MONTHLY"));
        TREND_DATA_LIST.add(new TrendData(11, "Action", LocalDate.of(2026, 5, 15), 1600, 240, 72000, "MONTHLY"));
        TREND_DATA_LIST.add(new TrendData(12, "Action", LocalDate.of(2026, 6, 15), 1800, 280, 84000, "MONTHLY"));

        // Weekly + monthly for "Comedy"
        TREND_DATA_LIST.add(new TrendData(13, "Comedy", LocalDate.of(2026, 1, 6), 180, 18, 3600, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(14, "Comedy", LocalDate.of(2026, 1, 13), 210, 22, 4400, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(15, "Comedy", LocalDate.of(2026, 1, 20), 195, 20, 4000, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(16, "Comedy", LocalDate.of(2026, 1, 27), 215, 30, 6000, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(17, "Comedy", LocalDate.of(2026, 2, 3), 190, 22, 4400, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(18, "Comedy", LocalDate.of(2026, 2, 10), 185, 20, 4000, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(19, "Comedy", LocalDate.of(2026, 2, 17), 180, 21, 4200, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(20, "Comedy", LocalDate.of(2026, 2, 24), 175, 22, 4400, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(21, "Comedy", LocalDate.of(2026, 3, 15), 700, 80, 16000, "MONTHLY"));
        TREND_DATA_LIST.add(new TrendData(22, "Comedy", LocalDate.of(2026, 4, 15), 650, 75, 15000, "MONTHLY"));
        TREND_DATA_LIST.add(new TrendData(23, "Comedy", LocalDate.of(2026, 5, 15), 900, 110, 22000, "MONTHLY"));
        TREND_DATA_LIST.add(new TrendData(24, "Comedy", LocalDate.of(2026, 6, 15), 850, 100, 20000, "MONTHLY"));

        // Weekly + monthly for "Fantasy"
        TREND_DATA_LIST.add(new TrendData(25, "Fantasy", LocalDate.of(2026, 1, 7), 140, 14, 7000, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(26, "Fantasy", LocalDate.of(2026, 1, 14), 155, 16, 7750, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(27, "Fantasy", LocalDate.of(2026, 1, 21), 148, 15, 7400, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(28, "Fantasy", LocalDate.of(2026, 1, 28), 157, 15, 7850, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(29, "Fantasy", LocalDate.of(2026, 2, 4), 162, 17, 8100, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(30, "Fantasy", LocalDate.of(2026, 2, 11), 168, 18, 8400, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(31, "Fantasy", LocalDate.of(2026, 2, 18), 172, 19, 8600, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(32, "Fantasy", LocalDate.of(2026, 2, 25), 163, 16, 8150, "WEEKLY"));
        TREND_DATA_LIST.add(new TrendData(33, "Fantasy", LocalDate.of(2026, 3, 15), 700, 80, 40000, "MONTHLY"));
        TREND_DATA_LIST.add(new TrendData(34, "Fantasy", LocalDate.of(2026, 4, 15), 550, 55, 27500, "MONTHLY"));
        TREND_DATA_LIST.add(new TrendData(35, "Fantasy", LocalDate.of(2026, 5, 15), 800, 90, 45000, "MONTHLY"));
        TREND_DATA_LIST.add(new TrendData(36, "Fantasy", LocalDate.of(2026, 6, 15), 950, 110, 55000, "MONTHLY"));
    }

    private static void populateShippers() {
        SHIPPERS.add(new Shipper(1, "Giao Hang Nhanh", "19006096", true));
        SHIPPERS.add(new Shipper(2, "Giao Hang Tiet Kiem", "19006319", true));
        SHIPPERS.add(new Shipper(3, "VietNam Post", "19005454", true));
        SHIPPERS.add(new Shipper(4, "J&T Express", "19008686", true));
    }

    private static void populateComicBooks() {
        ComicBook cb1 = new ComicBook();
        cb1.setComicId(1);
        cb1.setTitle("One Piece - Tap 100");
        cb1.setPrice(50000);
        cb1.setStock(20);
        COMIC_BOOKS.add(cb1);

        ComicBook cb2 = new ComicBook();
        cb2.setComicId(2);
        cb2.setTitle("Dragon Ball - Tap 50");
        cb2.setPrice(45000);
        cb2.setStock(0);
        COMIC_BOOKS.add(cb2);

        ComicBook cb3 = new ComicBook();
        cb3.setComicId(3);
        cb3.setTitle("Conan - Tap 90");
        cb3.setPrice(55000);
        cb3.setStock(10);
        COMIC_BOOKS.add(cb3);
    }

    private static void populateUsersAndOrders() {
        Customer customer = new Customer(1, "Nguyen Van A", "vana@gmail.com", "123456");
        USERS.add(customer);

        Admin admin = new Admin(999, "Admin", "admin@store.com", "secret");
        USERS.add(admin);

        try {
            Field dateField = Order.class.getDeclaredField("orderDate");
            dateField.setAccessible(true);

            // Order #201 - One Piece x3, Pending
            Order o1 = new Order();
            o1.setOrderId(201);
            o1.setCustomer(customer);
            o1.addOrderItem(new OrderItem(COMIC_BOOKS.get(0), 3, 50000));
            o1.setShippingAddress("123 Duong Le Loi, Q.1, TP.HCM");
            o1.setPhoneNumber("0909123456");
            dateField.set(o1, LocalDateTime.of(2026, 6, 1, 10, 0));
            ORDERS.add(o1);

            // Order #202 - One Piece x5, Pending
            Order o2 = new Order();
            o2.setOrderId(202);
            o2.setCustomer(customer);
            o2.addOrderItem(new OrderItem(COMIC_BOOKS.get(0), 5, 50000));
            o2.setShippingAddress("123 Duong Le Loi, Q.1, TP.HCM");
            o2.setPhoneNumber("0909123456");
            dateField.set(o2, LocalDateTime.of(2026, 6, 5, 14, 30));
            ORDERS.add(o2);

            // Order #203 - Conan x2, Pending
            Order o3 = new Order();
            o3.setOrderId(203);
            o3.setCustomer(customer);
            o3.addOrderItem(new OrderItem(COMIC_BOOKS.get(2), 2, 55000));
            o3.setShippingAddress("789 Nguyen Hue, Q.1, TP.HCM");
            o3.setPhoneNumber("0909987654");
            dateField.set(o3, LocalDateTime.of(2026, 6, 10, 9, 15));
            ORDERS.add(o3);

            // Order #204 - Dragon Ball x1 (out of stock), Pending
            Order o4 = new Order();
            o4.setOrderId(204);
            o4.setCustomer(customer);
            o4.addOrderItem(new OrderItem(COMIC_BOOKS.get(1), 1, 45000));
            o4.setShippingAddress("456 Pham Ngu Lao, Q.1, TP.HCM");
            o4.setPhoneNumber("0909777777");
            dateField.set(o4, LocalDateTime.of(2026, 5, 20, 11, 0));
            ORDERS.add(o4);

            // Order #205 - Conan x8, Pending (for cancellation)
            Order o5 = new Order();
            o5.setOrderId(205);
            o5.setCustomer(customer);
            o5.addOrderItem(new OrderItem(COMIC_BOOKS.get(2), 8, 55000));
            o5.setShippingAddress("321 Tran Hung Dao, Q.5, TP.HCM");
            o5.setPhoneNumber("0909666666");
            dateField.set(o5, LocalDateTime.of(2026, 6, 15, 16, 45));
            ORDERS.add(o5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FakeDatabase() {
        // Không cho phép khởi tạo.
    }
}