package demo.uc_order_history;

import controller.OrderHistoryController;
import model.*;
import service.impl.OrderHistoryServiceImpl;
import state.ShippedState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DemoOrderHistory {

    static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        Customer khachHang = new Customer();
        khachHang.setUserId(1);
        khachHang.setFullName("Nguyen Van A");
        khachHang.setEmail("vana@gmail.com");

        Customer khachHangMoi = new Customer();
        khachHangMoi.setUserId(2);
        khachHangMoi.setFullName("Tran Thi B");
        khachHangMoi.setEmail("thib@gmail.com");

        ComicBook onePiece = new ComicBook();
        onePiece.setComicId(1);
        onePiece.setTitle("One Piece - Tap 100");
        onePiece.setPrice(35000);
        onePiece.setStock(15);

        ComicBook naruto = new ComicBook();
        naruto.setComicId(2);
        naruto.setTitle("Naruto - Tap 72");
        naruto.setPrice(28000);
        naruto.setStock(0);

        ComicBook dragonBall = new ComicBook();
        dragonBall.setComicId(3);
        dragonBall.setTitle("Dragon Ball - Tap 42");
        dragonBall.setPrice(32000);
        dragonBall.setStock(8);

        Order don1 = new Order();
        don1.setOrderId(101);
        don1.setCustomer(khachHang);
        don1.setShippingAddress("123 Le Loi, Da Nang");
        don1.setPhoneNumber("0905123456");
        don1.addOrderItem(new OrderItem(onePiece, 2, onePiece.getPrice()));
        don1.addOrderItem(new OrderItem(naruto, 1, naruto.getPrice()));

        don1.setState(new ShippedState());

        Order don2 = new Order();
        don2.setOrderId(102);
        don2.setCustomer(khachHang);
        don2.setShippingAddress("456 Nguyen Van Linh, Da Nang");
        don2.setPhoneNumber("0905123456");
        don2.addOrderItem(new OrderItem(dragonBall, 3, dragonBall.getPrice()));

        try {
            java.lang.reflect.Field f = Order.class.getDeclaredField("orderDate");
            f.setAccessible(true);
            f.set(don2, LocalDateTime.now().minusDays(10));
        } catch (Exception e) {
        }
        don2.setState(new ShippedState());

        List<Order> danhSachFake = new ArrayList<>();
        danhSachFake.add(don1);
        danhSachFake.add(don2);

        OrderHistoryServiceImpl service = new OrderHistoryServiceImpl(danhSachFake);
        OrderHistoryController controller = new OrderHistoryController(service);

        scenarioA(controller, khachHang.getUserId());
        scenarioB(controller, khachHang.getUserId(), don1.getOrderId());
        scenarioC_HopLe(controller, khachHang.getUserId(), don1.getOrderId());
        scenarioC_QuaHan(controller, khachHang.getUserId(), don2.getOrderId());
        scenarioD_ChuaCoDon(controller, khachHangMoi.getUserId());
    }

    static void scenarioA(OrderHistoryController ctrl, int userId) {
        System.out.println("\n========== XEM LICH SU MUA HANG ==========");

        System.out.println("\n[BUOC 1] Khach hang truy cap \"Lich su mua hang\"");

        System.out.println("[BUOC 2] He thong lay danh sach don hang...");
        List<Order> danhSach = ctrl.xemDanhSach(userId);

        if (danhSach.isEmpty()) {
            System.out.println("  => Ban chua co don hang nao. Hay kham pha san pham! (E1)");
            return;
        }

        System.out.println("  => Tim thay " + danhSach.size() + " don hang:\n");
        inDanhSachDon(danhSach);

        System.out.println("\n[BUOC 3] Khach hang chon don #" + danhSach.get(0).getOrderId() + " de xem chi tiet");

        System.out.println("[BUOC 4] He thong hien thi chi tiet don hang...");
        Order chiTiet = ctrl.xemChiTiet(danhSach.get(0).getOrderId(), userId);

        if (chiTiet != null) {
            inChiTietDon(chiTiet);
        }
    }

    static void scenarioB(OrderHistoryController ctrl, int userId, int orderId) {
        System.out.println("\n========== MUA LAI DON HANG #" + orderId + " ==========");

        System.out.println("[BUOC 1] Khach hang nhan \"Mua lai\" tren don #" + orderId);
        System.out.println("[BUOC 2] He thong kiem tra ton kho va them vao gio hang...");

        boolean thanhCong = ctrl.muaLai(orderId, userId);

        if (thanhCong) {
            System.out.println("  => Da them cac san pham vao gio hang thanh cong!");
        } else {
            System.out.println("  => Khong tim thay don hang.");
        }
    }

    static void scenarioC_HopLe(OrderHistoryController ctrl, int userId, int orderId) {
        System.out.println("\n========== SCENARIO C1: YEU CAU DOI/TRA - CON HAN ==========");

        System.out.println("[BUOC 1] Khach hang chon san pham va nhap ly do doi/tra");
        String lyDo = "San pham bi loi trang in";

        System.out.println("[BUOC 2] He thong kiem tra thoi han 7 ngay...");
        ReturnRequest yeuCau = ctrl.doiTra(orderId, userId, lyDo);

        if (yeuCau != null) {
            System.out.println("  => Yeu cau doi/tra da duoc ghi nhan!");
            System.out.println("  Ma yeu cau : #" + yeuCau.getReturnId());
            System.out.println("  Ly do      : " + yeuCau.getReason());
            System.out.println("  Trang thai : " + yeuCau.getStatus());
            System.out.println("  Thoi gian  : " + yeuCau.getCreatedAt().format(fmt));
        } else {
            System.out.println("  => Khong the tao yeu cau doi/tra.");
        }
    }

    static void scenarioC_QuaHan(OrderHistoryController ctrl, int userId, int orderId) {
        System.out.println("\n========== YEU CAU DOI/TRA - QUA HAN ==========");

        System.out.println("[BUOC 1] Khach hang muon doi/tra don #" + orderId + " (dat 10 ngay truoc)");
        System.out.println("[BUOC 2] He thong kiem tra thoi han 7 ngay...");

        ReturnRequest yeuCau = ctrl.doiTra(orderId, userId, "Muon doi hang");

        if (yeuCau == null) {
            System.out.println("  => Da het thoi han doi/tra (chi duoc trong vong 7 ngay). (E2)");
        } else {
            System.out.println("  => Yeu cau duoc chap nhan (khong mong doi).");
        }
    }

    static void scenarioD_ChuaCoDon(OrderHistoryController ctrl, int userId) {
        System.out.println("\n========== CHUA CO DON HANG ==========");

        System.out.println("[BUOC 1] Khach hang moi truy cap \"Lich su mua hang\"");
        System.out.println("[BUOC 2] He thong kiem tra danh sach don hang...");

        List<Order> danhSach = ctrl.xemDanhSach(userId);

        if (danhSach.isEmpty()) {
            System.out.println("  => Ban chua co don hang nao. Hay kham pha san pham! (E1)");
        } else {
            inDanhSachDon(danhSach);
        }
    }

    static void inDanhSachDon(List<Order> danhSach) {
        System.out.println("  +--------+----------------------+----------------+------------------+");
        System.out.println("  | Ma don | Ngay dat             | Tong tien      | Trang thai       |");
        System.out.println("  +--------+----------------------+----------------+------------------+");
        for (Order o : danhSach) {
            System.out.printf("  | #%-5d | %-20s | %12.0f d | %-16s |%n",
                    o.getOrderId(),
                    o.getOrderDate().format(fmt),
                    o.getTotalAmount(),
                    o.getState().getStateName()
            );
        }
        System.out.println("  +--------+----------------------+----------------+------------------+");
    }

    static void inChiTietDon(Order o) {
        System.out.println("\n  ----- CHI TIET DON HANG #" + o.getOrderId() + " -----");
        System.out.println("  Khach hang   : " + o.getCustomer().getFullName());
        System.out.println("  Dia chi giao : " + o.getShippingAddress());
        System.out.println("  SDT          : " + o.getPhoneNumber());
        System.out.println("  Trang thai   : " + o.getState().getStateName());
        System.out.println("  Ngay dat     : " + o.getOrderDate().format(fmt));
        System.out.println("  San pham:");
        for (OrderItem item : o.getOrderItems()) {
            System.out.printf("    - %-25s x%d  %,.0f d%n",
                    item.getComicBook().getTitle(),
                    item.getQuantity(),
                    item.getSubTotal()
            );
        }
        System.out.printf("  Tong tien    : %,.0f dong%n", o.getTotalAmount());
        System.out.println("  -----------------------------------");
    }
}
