package demo.uc_analysis;

import controller.RegisterController;
import controller.ReportController;
import dto.RegisterRequest;
import model.*;
import state.ShippedState;
import database.FakeDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class DemoAnalysis {
    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println("          DEMO USE CASE UC-2.1: STATISTICS        ");
            System.out.println("==================================================");

            // Setup a registered customer using original RegisterController
            RegisterController registerController = new RegisterController();
            RegisterRequest registerRequest = new RegisterRequest(
                    "Nguyen Van A",
                    "vana@gmail.com",
                    "123456",
                    "123456"
            );
            Customer customer = registerController.register(registerRequest);

            // ========================================================================
            // UC-2.1 Revenue Statistics Integration Demo
            // ========================================================================
            System.out.println("\n========== UC-2.1 - REVENUE STATISTICS & REPORTING ==========");
            ReportController reportController = new ReportController();

            // Create admin
            Admin admin = new Admin(999, "Admin Manager", "admin@comicstore.com", "adminSecret123");
            FakeDatabase.USERS.add(admin);

            // Populate some dummy orders for June 2026 to verify BR2.1-1
            ComicBook comicBook = new ComicBook();
            comicBook.setComicId(1);
            comicBook.setTitle("One Piece");
            comicBook.setPrice(50000);
            comicBook.setStock(20);

            // Dummy order 1: Date 2026-06-01, Amount 250000.0, State ShippedState (Delivered) -> Eligible
            Order order1 = new Order();
            order1.setOrderId(101);
            order1.setCustomer(customer);
            order1.addOrderItem(new OrderItem(comicBook, 5, comicBook.getPrice())); // 5 * 50000 = 250000
            order1.setState(new ShippedState());
            // Set custom order date
            java.lang.reflect.Field dateField = Order.class.getDeclaredField("orderDate");
            dateField.setAccessible(true);
            dateField.set(order1, LocalDateTime.of(2026, 6, 1, 10, 0));
            FakeDatabase.ORDERS.add(order1);

            // Dummy order 2: Date 2026-06-10, Amount 150000.0, State PendingState, Paid = true -> Eligible
            Order order2 = new Order();
            order2.setOrderId(102);
            order2.setCustomer(customer);
            order2.addOrderItem(new OrderItem(comicBook, 3, comicBook.getPrice())); // 3 * 50000 = 150000
            dateField.set(order2, LocalDateTime.of(2026, 6, 10, 15, 30));
            FakeDatabase.ORDERS.add(order2);

            Payment payment2 = new Payment();
            payment2.setPaymentId(802);
            payment2.setOrder(order2);
            payment2.setPaid(true);
            FakeDatabase.PAYMENTS.add(payment2);

            // Dummy order 3: Date 2026-06-20, Amount 300000.0, State PendingState, Paid = false -> Excluded (BR2.1-1)
            Order order3 = new Order();
            order3.setOrderId(103);
            order3.setCustomer(customer);
            order3.addOrderItem(new OrderItem(comicBook, 6, comicBook.getPrice())); // 6 * 50000 = 300000
            dateField.set(order3, LocalDateTime.of(2026, 6, 20, 11, 45));
            FakeDatabase.ORDERS.add(order3);

            Payment payment3 = new Payment();
            payment3.setPaymentId(803);
            payment3.setOrder(order3);
            payment3.setPaid(false);
            FakeDatabase.PAYMENTS.add(payment3);

            // Dummy order 4: Date 2026-05-15, Amount 500000.0, State ShippedState -> Out of date filter range
            Order order4 = new Order();
            order4.setOrderId(104);
            order4.setCustomer(customer);
            order4.addOrderItem(new OrderItem(comicBook, 10, comicBook.getPrice())); // 10 * 50000 = 500000
            order4.setState(new ShippedState());
            dateField.set(order4, LocalDateTime.of(2026, 5, 15, 9, 0));
            FakeDatabase.ORDERS.add(order4);

            // Scenario A: Basic Flow - successful stats for June 2026
            System.out.println("\n[Scenario A] Requesting revenue report for June 2026");
            LocalDate startFilter = LocalDate.of(2026, 6, 1);
            LocalDate endFilter = LocalDate.of(2026, 6, 30);
            AnalysisReport report = reportController.generateRevenueReport(startFilter, endFilter, false);
            System.out.println("[UI] Statistics loaded! Total Calculated Revenue: $" + report.getTotalRevenue());

            // Scenario B: Alternative Flow 5a - Export report to Excel
            System.out.println("\n[Scenario B] Requesting Excel export file");
            reportController.exportExcel(report);

            // Scenario C: Exception Flow 5b - Filter date range with zero transaction data
            System.out.println("\n[Scenario C] Requesting statistics for date range with zero data");
            LocalDate emptyStart = LocalDate.of(2026, 7, 10);
            LocalDate emptyEnd = LocalDate.of(2026, 7, 20);
            try {
                reportController.generateRevenueReport(emptyStart, emptyEnd, false);
            } catch (NoSuchElementException ex) {
                System.out.println("[UI WARNING] " + ex.getMessage());
            }

            // Scenario D: Exception Flow 5c - Database Failure
            System.out.println("\n[Scenario D] Simulating database connection failure during statistics query");
            try {
                reportController.generateRevenueReport(startFilter, endFilter, true);
            } catch (java.sql.SQLException ex) {
                System.out.println("[UI ERROR] " + ex.getMessage());
                System.out.println("[UI ACTION] Terminating use case statistics view.");
            }
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
