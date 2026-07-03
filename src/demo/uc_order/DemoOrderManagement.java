package demo.uc_order;

import controller.OrderController;
import database.FakeDatabase;
import model.*;
import service.NotificationService;
import service.OrderService;
import service.ShippingService;
import service.impl.NotificationServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ShippingServiceImpl;
import state.ShippedState;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Demo Tuong Tac: Quan ly don hang Admin (UC-10)
 * - Du lieu co san trong FakeDatabase
 * - Tung buoc nhan Enter de tiep tuc
 * - Chi nhap lieu khi Admin can nhap
 */
public class DemoOrderManagement {

    private static final Scanner sc = new Scanner(System.in);
    private static final OrderController controller = new OrderController();
    private static final ShippingService shippingService = new ShippingServiceImpl();
    private static final NotificationService notificationService = new NotificationServiceImpl();
    private static final OrderService orderService = new OrderServiceImpl();

    public static void main(String[] args) {
        while (true) {
            System.out.println("========== QUAN LY DON HANG (UC-10) ==========");
            System.out.println("  1. Luong co ban: Phe duyet & Van chuyen");
            System.out.println("  2. Luong thay the: Huy don");
            System.out.println("  3. Luong thay the: Het hang (loi)");
            System.out.println("  4. Luong thay the: Tu dong gan shipper");
            System.out.println("  5. Luong thay the: Khong co DVVC (cho shipper)");
            System.out.println("  6. Luong thay the: Thieu thong tin giao hang");
            System.out.println("  7. Exception: Huy don da giao");
            System.out.println("  0. Thoat");
            System.out.print("Chon: ");
            String chon = sc.nextLine().trim();
            System.out.println();

            switch (chon) {
                case "1" -> scenarioA();
                case "2" -> scenarioB();
                case "3" -> scenarioC();
                case "4" -> scenarioD();
                case "5" -> scenarioF();
                case "6" -> scenarioG();
                case "7" -> scenarioE();
                case "0" -> { System.out.println("Tam biet!"); return; }
                default -> System.out.println("Khong hop le.\n");
            }
        }
    }

    /* ================================================================
       HELPERS
       ================================================================ */
    private static void pause() {
        System.out.print("  (Nhan Enter de tiep tuc...)");
        sc.nextLine();
        System.out.println();
    }

    private static void hienThiDanhSach() {
        System.out.println("--- DANH SACH DON HANG ---");
        List<Order> list = controller.getOrderList(new HashMap<>());
        if (list.isEmpty()) { System.out.println("  (trong)\n"); return; }
        for (Order o : list) {
            String otems = "";
            for (OrderItem item : o.getOrderItems())
                otems += item.getComicBook().getTitle() + " x" + item.getQuantity() + " ";
            System.out.println("  #" + o.getOrderId() + " | " + o.getCustomer().getFullName()
                    + " | " + o.getState().getStateName()
                    + " | $" + String.format("%.0f", o.getTotalAmount())
                    + " | " + otems);
        }
        System.out.println();
    }

    private static void hienThiChiTiet(int id) {
        Order o = controller.getOrderDetails(id);
        if (o == null) { System.out.println("  Khong tim thay don hang.\n"); return; }
        System.out.println("  Khach: " + o.getCustomer().getFullName());
        System.out.println("  Tong: $" + String.format("%.0f", o.getTotalAmount()));
        System.out.println("  Trang thai: " + o.getState().getStateName());
        System.out.println("  Ngay: " + o.getOrderDate());
        System.out.println("  Dia chi: " + o.getShippingAddress());
        System.out.println("  SDT: " + o.getPhoneNumber());
        System.out.println("  San pham:");
        for (OrderItem item : o.getOrderItems())
            System.out.println("    - " + item.getComicBook().getTitle()
                    + " x" + item.getQuantity() + " = $" + item.getUnitPrice());
    }

    private static List<Shipper> hienThiShipper() {
        List<Shipper> shippers = shippingService.getAvailableShippers();
        for (Shipper s : shippers)
            System.out.println("  #" + s.getShipperID() + " - " + s.getName());
        return shippers;
    }

    private static int chonDonHang() {
        System.out.print("  Nhap ID don hang: ");
        int oid = Integer.parseInt(sc.nextLine().trim());
        System.out.println();
        return oid;
    }

    /* ================================================================
       SCENARIO A: PHE DUYET & VAN CHUYEN (Happy Path)
       ================================================================ */
    private static void scenarioA() {
        System.out.println("========== SCENARIO A: PHE DUYET & VAN CHUYEN ==========\n");

        /* B1 */
        System.out.println("--- BUOC 1: Admin truy cap chuc nang Quan ly don hang ---");
        pause();

        /* B2 */
        System.out.println("--- BUOC 2 (HT): Hien thi danh sach don hang voi bo loc mac dinh ---");
        hienThiDanhSach();
        pause();

        /* B3 */
        System.out.println("--- BUOC 3: Admin chon dieu kien loc (Enter de bo qua, lay mac dinh) ---");
        pause();

        /* B4 */
        System.out.println("--- BUOC 4 (HT): Cap nhat danh sach don hang phu hop ---");
        hienThiDanhSach();
        pause();

        /* B5 */
        System.out.println("--- BUOC 5: Admin chon don hang can xu ly ---");
        int oid = chonDonHang();
        pause();

        /* B6 */
        System.out.println("--- BUOC 6 (HT): Hien thi chi tiet don hang ---");
        hienThiChiTiet(oid);
        pause();

        /* B6a */
        System.out.println("--- BUOC 6a: Admin chon \"Xu ly\" ---");
        pause();

        /* B7 */
        System.out.println("--- BUOC 7: Admin kiem tra va nhan phe duyet ---");
        try {
            controller.approveOrder(oid);
        } catch (Exception e) {
            System.out.println("  LOI: " + e.getMessage() + "\n");
            return;
        }
        pause();

        /* B8 -> B9 */
        System.out.println("--- BUOC 8 (HT): Kiem tra ton kho -> Du hang ---");
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        System.out.println("--- BUOC 9 (HT): Cap nhat trang thai APPROVED ---");
        System.out.println("  Trang thai: " + controller.getOrderDetails(oid).getState().getStateName());
        pause();

        /* B10 */
        System.out.println("--- BUOC 10 (HT): Hien thi danh sach don vi van chuyen ---");
        hienThiShipper();
        pause();

        /* B10a */
        System.out.println("--- BUOC 10a (HT): Co don vi van chuyen? -> Co ---");
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        pause();

        /* B10c -> B11: Chon DVVC */
        System.out.println("--- BUOC 10c: Admin chon \"Chon don vi van chuyen\" ---");
        System.out.println("--- BUOC 11: Admin chon don vi van chuyen ---");
        System.out.print("  Nhap so DVVC (1-4): ");
        long sid = Long.parseLong(sc.nextLine().trim());
        TrackingInfo info = controller.assignShipper(oid, sid);
        if (info != null)
            System.out.println("  Da gan: " + info.getShipper().getName() + " - Ma: " + info.getTrackingCode());
        else
            System.out.println("  Shipper khong ton tai!");
        pause();

        if (info == null) { System.out.println("\n  === KET THUC SCENARIO A ===\n"); return; }

        /* B12 */
        System.out.println("--- BUOC 12 (HT): Gan don vi van chuyen, tao ma van don ---");
        System.out.println("  Ma van don: " + info.getTrackingCode());
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        System.out.println("  (EF4: Trung ma van don -> tao ma moi)");
        pause();

        /* B12a */
        System.out.println("--- BUOC 12a (HT): Thong tin du? -> Du ---");
        pause();

        /* B13 */
        System.out.println("--- BUOC 13 (HT): Tao va in van don ---");
        shippingService.createShippingLabel(oid);
        pause();

        /* B14 */
        System.out.println("--- BUOC 14 (HT): Gui thong bao cho khach hang ---");
        notificationService.sendNotifications(oid);
        System.out.println("  (EF3: Loi gui thong bao -> retry & log)");
        pause();

        /* B15 */
        System.out.println("--- BUOC 15 (HT): Cap nhat trang thai DISPATCHED ---");
        orderService.updateStatus(oid, "DISPATCHED");
        System.out.println("  Trang thai: " + controller.getOrderDetails(oid).getState().getStateName());
        System.out.println("\n  === KET THUC SCENARIO A ===\n");
    }

    /* ================================================================
       SCENARIO B: HUY DON
       ================================================================ */
    private static void scenarioB() {
        System.out.println("========== SCENARIO B: HUY DON ==========\n");

        System.out.println("--- BUOC 1: Admin truy cap chuc nang Quan ly don hang ---");
        pause();

        System.out.println("--- BUOC 2 (HT): Hien thi danh sach don hang ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 3: Admin chon dieu kien loc (Enter de bo qua) ---");
        pause();

        System.out.println("--- BUOC 4 (HT): Cap nhat danh sach ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 5: Admin chon don hang can xu ly ---");
        int oid = chonDonHang();
        pause();

        System.out.println("--- BUOC 6 (HT): Hien thi chi tiet don hang ---");
        hienThiChiTiet(oid);
        pause();

        System.out.println("--- BUOC 6a: Admin chon \"Huy don\" ---");
        pause();

        System.out.println("--- BUOC 6b: Admin nhan huy don ---");
        System.out.println("--- BUOC 6c: Admin nhap ly do huy ---");
        System.out.print("  Nhap ly do huy: ");
        String reason = sc.nextLine().trim();
        if (reason.isEmpty()) reason = "Khach hang doi y kien";
        System.out.println();

        try {
            Order c = controller.cancelOrder(oid, reason);
            System.out.println("--- BUOC 6d (HT): Kiem tra trang thai don -> OK (Pending) ---");
            pause();
            System.out.println("--- BUOC 6e (HT): Cap nhat CANCELLED ---");
            pause();
            System.out.println("--- BUOC 6f (HT): Hoan tien cho khach hang ---");
            pause();
            System.out.println("--- BUOC 6g (HT): Gui thong bao huy don ---");
            System.out.println("  (EF3: Loi gui thong bao -> retry & log)");
            System.out.println("  Trang thai: " + c.getState().getStateName());
            System.out.println("  Ly do: " + c.getCancellationReason());
        } catch (Exception e) {
            System.out.println("  LOI: " + e.getMessage());
        }
        System.out.println("\n  === KET THUC SCENARIO B ===\n");
    }

    /* ================================================================
       SCENARIO C: HET HANG (Exception)
       ================================================================ */
    private static void scenarioC() {
        System.out.println("========== SCENARIO C: HET HANG (Exception) ==========\n");
        System.out.println("  (Dung don #204 - Dragon Ball, ton kho = 0)\n");

        System.out.println("--- BUOC 1: Admin truy cap chuc nang Quan ly don hang ---");
        pause();

        System.out.println("--- BUOC 2 (HT): Hien thi danh sach don hang voi bo loc mac dinh ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 3: Admin chon dieu kien loc (Enter de bo qua) ---");
        pause();

        System.out.println("--- BUOC 4 (HT): Cap nhat danh sach don hang phu hop ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 5: Admin chon don hang can xu ly ---");
        int oid = chonDonHang();
        pause();

        System.out.println("--- BUOC 6 (HT): Hien thi chi tiet don hang ---");
        hienThiChiTiet(oid);
        pause();

        System.out.println("--- BUOC 6a: Admin chon \"Xu ly\" ---");
        pause();

        System.out.println("--- BUOC 7: Admin kiem tra va nhan phe duyet ---");
        try {
            controller.approveOrder(oid);
            System.out.println("  Phe duyet thanh cong (khong ngo!)");
        } catch (Exception e) {
            System.out.println("--- BUOC 8 (HT): Kiem tra ton kho -> HET HANG ---");
            pause();
            System.out.println("--- BUOC 8a (HT): Phat hien het hang ---");
            pause();
            System.out.println("--- BUOC 8b (HT): Thong bao het hang ---");
            System.out.println("  " + e.getMessage());
        }
        System.out.println("\n  === KET THUC SCENARIO C ===\n");
    }

    /* ================================================================
       SCENARIO D: TU DONG GAN SHIPPER
       ================================================================ */
    private static void scenarioD() {
        System.out.println("========== SCENARIO D: TU DONG GAN SHIPPER ==========\n");

        System.out.println("--- BUOC 1: Admin truy cap chuc nang Quan ly don hang ---");
        pause();

        System.out.println("--- BUOC 2 (HT): Hien thi danh sach don hang voi bo loc mac dinh ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 3: Admin chon dieu kien loc (Enter de bo qua) ---");
        pause();

        System.out.println("--- BUOC 4 (HT): Cap nhat danh sach don hang phu hop ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 5: Admin chon don hang can xu ly ---");
        int oid = chonDonHang();
        pause();

        System.out.println("--- BUOC 6 (HT): Chi tiet don hang ---");
        hienThiChiTiet(oid);
        pause();

        System.out.println("--- BUOC 6a: Admin chon \"Xu ly\" ---");
        pause();
        System.out.println("--- BUOC 7: Admin phe duyet ---");
        try {
            controller.approveOrder(oid);
            System.out.println("  Da phe duyet.");
        } catch (Exception e) {
            System.out.println("  LOI: " + e.getMessage());
            return;
        }
        pause();

        System.out.println("--- BUOC 8 (HT): Kiem tra ton kho -> Du hang ---");
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        System.out.println("--- BUOC 9 (HT): APPROVED ---");
        pause();

        System.out.println("--- BUOC 10 (HT): Danh sach DVVC ---");
        hienThiShipper();
        pause();

        System.out.println("--- BUOC 10a (HT): Co DVVC? -> Co ---");
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        pause();

        System.out.println("--- BUOC 10c: Admin chon \"Tu dong gan\" ---");
        pause();

        System.out.println("--- BUOC 11a (HT): Tu dong gan don vi van chuyen ---");
        TrackingInfo info = controller.autoAssignShipper(oid);
        if (info != null)
            System.out.println("  Da gan: " + info.getShipper().getName() + " - Ma: " + info.getTrackingCode());
        else
            System.out.println("  Khong co shipper kha dung!");
        pause();

        if (info == null) { System.out.println("\n  === KET THUC SCENARIO D ===\n"); return; }

        System.out.println("--- BUOC 12 (HT): Gan don vi van chuyen, tao ma van don ---");
        System.out.println("  Ma van don: " + info.getTrackingCode());
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        System.out.println("  (EF4: Trung ma van don -> tao ma moi)");
        pause();
        System.out.println("--- BUOC 12a (HT): Thong tin du? -> Du ---");
        pause();
        System.out.println("--- BUOC 13 (HT): Tao va in van don ---");
        shippingService.createShippingLabel(oid);
        pause();
        System.out.println("--- BUOC 14 (HT): Gui thong bao cho khach hang ---");
        notificationService.sendNotifications(oid);
        pause();
        System.out.println("--- BUOC 15 (HT): Cap nhat trang thai DISPATCHED ---");
        orderService.updateStatus(oid, "DISPATCHED");
        System.out.println("  Trang thai: " + controller.getOrderDetails(oid).getState().getStateName());
        System.out.println("\n  === KET THUC SCENARIO D ===\n");
    }

    /* ================================================================
       SCENARIO F: KHONG CO DON VI VAN CHUYEN (10b + wait loop)
       ================================================================ */
    private static void scenarioF() {
        System.out.println("========== SCENARIO F: KHONG CO DVVC (CHO SHIPPER) ==========\n");

        System.out.println("--- BUOC 1: Admin truy cap chuc nang Quan ly don hang ---");
        pause();

        System.out.println("--- BUOC 2 (HT): Hien thi danh sach don hang voi bo loc mac dinh ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 3: Admin chon dieu kien loc (Enter de bo qua) ---");
        pause();

        System.out.println("--- BUOC 4 (HT): Cap nhat danh sach don hang phu hop ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 5: Admin chon don hang can xu ly ---");
        int oid = chonDonHang();
        pause();

        System.out.println("--- BUOC 6 (HT): Hien thi chi tiet don hang ---");
        hienThiChiTiet(oid);
        pause();

        System.out.println("--- BUOC 6a: Admin chon \"Xu ly\" ---");
        pause();

        System.out.println("--- BUOC 7: Admin kiem tra va nhan phe duyet ---");
        try { controller.approveOrder(oid); }
        catch (Exception e) { System.out.println("  LOI: " + e.getMessage() + "\n"); return; }
        pause();

        System.out.println("--- BUOC 8 (HT): Kiem tra ton kho -> Du hang ---");
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        System.out.println("--- BUOC 9 (HT): Cap nhat trang thai APPROVED ---");
        System.out.println("  Trang thai: " + controller.getOrderDetails(oid).getState().getStateName());
        pause();

        System.out.println("--- BUOC 10 (HT): Hien thi danh sach don vi van chuyen ---");
        hienThiShipper();
        pause();

        System.out.println("--- BUOC 10a (HT): Co don vi van chuyen? -> Khong ---");
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        pause();

        System.out.println("--- BUOC 10b (HT): Thong bao khong co don vi van chuyen ---");
        System.out.println("  => Doi den khi co shipper...");
        for (int i = 1; i <= 3; i++) {
            System.out.print("  (Kiem tra lan " + i + "/3 - Enter de tiep tuc...)");
            sc.nextLine();
            System.out.println("    Chua co shipper kha dung.");
        }
        System.out.println("  (Co shipper moi xuat hien!)");
        pause();

        System.out.println("--- BUOC 10a (HT): Co don vi van chuyen? -> Co ---");
        pause();

        System.out.println("--- BUOC 10c: Admin chon \"Tu dong gan\" ---");
        pause();

        System.out.println("--- BUOC 11a (HT): Tu dong gan don vi van chuyen ---");
        TrackingInfo info = controller.autoAssignShipper(oid);
        if (info != null)
            System.out.println("  Da gan: " + info.getShipper().getName() + " - Ma: " + info.getTrackingCode());
        else
            System.out.println("  Khong co shipper kha dung!");
        pause();

        if (info == null) { System.out.println("\n  === KET THUC SCENARIO F ===\n"); return; }

        System.out.println("--- BUOC 12 (HT): Gan don vi van chuyen, tao ma van don ---");
        System.out.println("  Ma van don: " + info.getTrackingCode());
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        System.out.println("  (EF4: Trung ma van don -> tao ma moi)");
        pause();

        System.out.println("--- BUOC 12a (HT): Thong tin du? -> Du ---");
        pause();

        System.out.println("--- BUOC 13 (HT): Tao va in van don ---");
        shippingService.createShippingLabel(oid);
        pause();

        System.out.println("--- BUOC 14 (HT): Gui thong bao cho khach hang ---");
        notificationService.sendNotifications(oid);
        System.out.println("  (EF3: Loi gui thong bao -> retry & log)");
        pause();

        System.out.println("--- BUOC 15 (HT): Cap nhat trang thai DISPATCHED ---");
        orderService.updateStatus(oid, "DISPATCHED");
        System.out.println("  Trang thai: " + controller.getOrderDetails(oid).getState().getStateName());
        System.out.println("\n  === KET THUC SCENARIO F ===\n");
    }

    /* ================================================================
       SCENARIO G: THIEU THONG TIN GIAO HANG (12b-12c + loop)
       ================================================================ */
    private static void scenarioG() {
        System.out.println("========== SCENARIO G: THIEU THONG TIN GIAO HANG ==========\n");
        System.out.println("  (Dung don #205 - thieu so dien thoai)\n");

        System.out.println("--- BUOC 1: Admin truy cap chuc nang Quan ly don hang ---");
        pause();

        System.out.println("--- BUOC 2 (HT): Hien thi danh sach don hang voi bo loc mac dinh ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 3: Admin chon dieu kien loc (Enter de bo qua) ---");
        pause();

        System.out.println("--- BUOC 4 (HT): Cap nhat danh sach don hang phu hop ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 5: Admin chon don hang can xu ly ---");
        int oid = chonDonHang();
        pause();

        System.out.println("--- BUOC 6 (HT): Hien thi chi tiet don hang ---");
        hienThiChiTiet(oid);
        pause();

        System.out.println("--- BUOC 6a: Admin chon \"Xu ly\" ---");
        pause();

        System.out.println("--- BUOC 7: Admin kiem tra va nhan phe duyet ---");
        try { controller.approveOrder(oid); }
        catch (Exception e) { System.out.println("  LOI: " + e.getMessage() + "\n"); return; }
        pause();

        System.out.println("--- BUOC 8 (HT): Kiem tra ton kho -> Du hang ---");
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        System.out.println("--- BUOC 9 (HT): Cap nhat trang thai APPROVED ---");
        System.out.println("  Trang thai: " + controller.getOrderDetails(oid).getState().getStateName());
        pause();

        System.out.println("--- BUOC 10 (HT): Hien thi danh sach don vi van chuyen ---");
        hienThiShipper();
        pause();

        System.out.println("--- BUOC 10a (HT): Co don vi van chuyen? -> Co ---");
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        pause();

        System.out.println("--- BUOC 10c: Admin chon \"Chon don vi van chuyen\" ---");
        System.out.println("--- BUOC 11: Admin chon don vi van chuyen ---");
        System.out.print("  Nhap so DVVC (1-4): ");
        long sid = Long.parseLong(sc.nextLine().trim());
        TrackingInfo info = controller.assignShipper(oid, sid);
        if (info != null)
            System.out.println("  Da gan: " + info.getShipper().getName() + " - Ma: " + info.getTrackingCode());
        else
            System.out.println("  Shipper khong ton tai!");
        pause();

        if (info == null) { System.out.println("\n  === KET THUC SCENARIO G ===\n"); return; }

        System.out.println("--- BUOC 12 (HT): Gan don vi van chuyen, tao ma van don ---");
        System.out.println("  Ma van don: " + info.getTrackingCode());
        System.out.println("  (EF1/EF2: Loi he thong/DB -> retry & log)");
        System.out.println("  (EF4: Trung ma van don -> tao ma moi)");
        pause();

        System.out.println("--- BUOC 12a (HT): Thong tin du? -> Khong du (thieu SDT) ---");
        pause();

        /* Simulate 12b-12c loop */
        System.out.println("--- BUOC 12b (HT): Yeu cau bo sung thong tin ---");
        System.out.println("  Thieu: So dien thoai nguoi nhan");
        pause();

        System.out.println("--- BUOC 12c: Admin bo sung thong tin ---");
        System.out.print("  Nhap SDT: ");
        String phone = sc.nextLine().trim();
        Order order = controller.getOrderDetails(oid);
        order.setPhoneNumber(phone);
        System.out.println("  Da cap nhat SDT: " + phone);
        pause();

        System.out.println("--- (Quay lai) BUOC 12 (HT): Gan don vi van chuyen, tao ma van don ---");
        System.out.println("  Ma van don: " + info.getTrackingCode());
        pause();

        System.out.println("--- BUOC 12a (HT): Thong tin du? -> Du ---");
        pause();

        System.out.println("--- BUOC 13 (HT): Tao va in van don ---");
        shippingService.createShippingLabel(oid);
        pause();

        System.out.println("--- BUOC 14 (HT): Gui thong bao cho khach hang ---");
        notificationService.sendNotifications(oid);
        System.out.println("  (EF3: Loi gui thong bao -> retry & log)");
        pause();

        System.out.println("--- BUOC 15 (HT): Cap nhat trang thai DISPATCHED ---");
        orderService.updateStatus(oid, "DISPATCHED");
        System.out.println("  Trang thai: " + controller.getOrderDetails(oid).getState().getStateName());
        System.out.println("\n  === KET THUC SCENARIO G ===\n");
    }

    /* ================================================================
       SCENARIO E: HUY DON DA GIAO (Exception)
       ================================================================ */
    private static void scenarioE() {
        System.out.println("========== SCENARIO E: HUY DON DA GIAO (Exception) ==========\n");

        System.out.println("--- Tao don hang da giao (SHIPPED) de test ---");
        int oid = 300;
        Customer customer = null;
        for (User u : FakeDatabase.USERS) {
            if (u instanceof Customer) { customer = (Customer) u; break; }
        }
        if (customer == null) {
            System.out.println("  Khong co khach hang trong DB.\n");
            return;
        }
        Order shipped = new Order();
        shipped.setOrderId(oid);
        shipped.setCustomer(customer);
        if (!FakeDatabase.COMIC_BOOKS.isEmpty())
            shipped.addOrderItem(new OrderItem(FakeDatabase.COMIC_BOOKS.get(0), 1,
                    FakeDatabase.COMIC_BOOKS.get(0).getPrice()));
        try {
            Field dateField = Order.class.getDeclaredField("orderDate");
            dateField.setAccessible(true);
            dateField.set(shipped, LocalDateTime.now());
        } catch (Exception ignored) {}
        shipped.setState(new ShippedState());
        FakeDatabase.ORDERS.add(shipped);
        System.out.println("  Da tao don #" + oid + " (trang thai: SHIPPED)\n");

        System.out.println("--- BUOC 1: Admin truy cap chuc nang Quan ly don hang ---");
        pause();

        System.out.println("--- BUOC 2 (HT): Hien thi danh sach don hang voi bo loc mac dinh ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 3: Admin chon dieu kien loc (Enter de bo qua) ---");
        pause();

        System.out.println("--- BUOC 4 (HT): Cap nhat danh sach don hang phu hop ---");
        hienThiDanhSach();
        pause();

        System.out.println("--- BUOC 5: Admin chon don hang can xu ly ---");
        System.out.println("  Chon don #" + oid);
        pause();

        System.out.println("--- BUOC 6 (HT): Hien thi chi tiet don hang ---");
        hienThiChiTiet(oid);
        pause();

        System.out.println("--- BUOC 6a: Admin chon \"Huy don\" ---");
        pause();

        System.out.println("--- BUOC 6b: Admin nhan huy don ---");
        System.out.println("--- BUOC 6c: Admin nhap ly do huy ---");
        System.out.print("  Nhap ly do huy: ");
        String reason = sc.nextLine().trim();
        if (reason.isEmpty()) reason = "Test huy don da giao";
        System.out.println();

        System.out.println("--- BUOC 6d (HT): Kiem tra trang thai don -> SHIPPED -> Khong the huy! ---");
        try {
            controller.cancelOrder(oid, reason);
            System.out.println("  Huy thanh cong (sai)!");
        } catch (Exception e) {
            System.out.println("EF1/EF2: LOI HE THONG / NGHIEP VU - " + e.getMessage());
            System.out.println("  (Ghi log loi va thong bao cho Admin)");
        }
        System.out.println("\n  === KET THUC SCENARIO E ===\n");
    }
}
