package demo.uc_cart;

import controller.CartController;
import model.Cart;
import model.CartItem;
import model.ComicBook;
import model.Customer;
import service.impl.CartServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * UC_GH - Demo Quan ly gio hang
 *
 * Basic Flow - Them san pham, xem gio, tinh tong tien
 * Alternative Flow A1 - SP da co trong gio -> tang so luong
 * Cap nhat so luong / xoa san pham
 * Exception Flow E1 - SP het hang
 * Exception Flow E2 - Vuot ton kho
 * Exception Flow E3 - Gio hang trong
 */
public class DemoCart {

    public static void main(String[] args) {

        // ===== SETUP =====
        Customer khachHang = new Customer();
        khachHang.setUserId(1);
        khachHang.setFullName("Nguyen Van A");

        ComicBook onePiece = new ComicBook();
        onePiece.setComicId(1);
        onePiece.setTitle("One Piece - Tap 100");
        onePiece.setPrice(35000);
        onePiece.setStock(10);

        ComicBook naruto = new ComicBook();
        naruto.setComicId(2);
        naruto.setTitle("Naruto - Tap 72");
        naruto.setPrice(28000);
        naruto.setStock(5);

        ComicBook hetHang = new ComicBook();
        hetHang.setComicId(3);
        hetHang.setTitle("Bleach - Tap 74");
        hetHang.setPrice(30000);
        hetHang.setStock(0); // het hang

        List<ComicBook> kho = new ArrayList<>();
        kho.add(onePiece);
        kho.add(naruto);
        kho.add(hetHang);

        CartController ctrl = new CartController(new CartServiceImpl(kho));

        // ===== CHAY SCENARIO =====
        scenarioA(ctrl, khachHang, onePiece, naruto);
        scenarioB(ctrl, khachHang, onePiece);
        scenarioC(ctrl, khachHang, naruto);
        scenarioD(ctrl, khachHang, hetHang);
        scenarioE(ctrl, khachHang, onePiece);
        scenarioF(ctrl, khachHang);
    }

    // Basic Flow - Them san pham va xem gio hang
    static void scenarioA(CartController ctrl, Customer kh, ComicBook sp1, ComicBook sp2) {
        System.out.println("\n========== Them sp & xem gio hang ==========");

        System.out.println("[BUOC 1] Khach hang chon \"" + sp1.getTitle() + "\", nhan Them vao gio");
        try {
            ctrl.themVaoGio(kh, sp1.getComicId(), 2);
            System.out.println("  => Da them thanh cong!");
        } catch (Exception e) {
            System.out.println("  => Loi: " + e.getMessage());
        }

        System.out.println("[BUOC 2] Khach hang chon \"" + sp2.getTitle() + "\", nhan Them vao gio");
        try {
            ctrl.themVaoGio(kh, sp2.getComicId(), 1);
            System.out.println("  => Da them thanh cong!");
        } catch (Exception e) {
            System.out.println("  => Loi: " + e.getMessage());
        }

        System.out.println("[BUOC 3] Khach hang truy cap trang gio hang");
        inGioHang(ctrl.xemGioHang(kh));
    }

    // Alternative Flow A1 - SP da co trong gio
    static void scenarioB(CartController ctrl, Customer kh, ComicBook sp) {
        System.out.println("\n========== Sp da co trong gio, tang so luong ==========");

        System.out.println("[BUOC 1] Khach hang them \"" + sp.getTitle() + "\" lan 2 (da co trong gio)");
        try {
            ctrl.themVaoGio(kh, sp.getComicId(), 1);
            System.out.println("  => He thong tang so luong thay vi them moi.");
        } catch (Exception e) {
            System.out.println("  => Loi: " + e.getMessage());
        }

        inGioHang(ctrl.xemGioHang(kh));
    }

    // Cap nhat so luong va xoa san pham
    static void scenarioC(CartController ctrl, Customer kh, ComicBook sp) {
        System.out.println("\n========== Cap nhat gio hang ==========");

        System.out.println("[BUOC 1] Khach hang chinh so luong \"" + sp.getTitle() + "\" thanh 3");
        try {
            ctrl.capNhatSoLuong(kh, sp.getComicId(), 3);
            System.out.println("  => Da cap nhat so luong.");
        } catch (Exception e) {
            System.out.println("  => Loi: " + e.getMessage());
        }
        inGioHang(ctrl.xemGioHang(kh));

        System.out.println("[BUOC 2] Khach hang xoa \"" + sp.getTitle() + "\" khoi gio");
        ctrl.xoaSanPham(kh, sp.getComicId());
        System.out.println("  => Da xoa san pham.");
        inGioHang(ctrl.xemGioHang(kh));
    }
    // Exception Flow E1 - SP het hang
    static void scenarioD(CartController ctrl, Customer kh, ComicBook sp) {
        System.out.println("\n========== Sp het hang ==========");

        System.out.println("[BUOC 1] Khach hang them \"" + sp.getTitle() + "\" (het hang)");
        try {
            ctrl.themVaoGio(kh, sp.getComicId(), 1);
        } catch (IllegalStateException e) {
            System.out.println("  => " + e.getMessage() + " (E1)");
        } catch (Exception e) {
            System.out.println("  => Loi: " + e.getMessage());
        }
    }

    // SCENARIO E: Exception Flow E2 - Vuot ton kho
    static void scenarioE(CartController ctrl, Customer kh, ComicBook sp) {
        System.out.println("\n========== Vuot ton kho ==========");

        System.out.println("[BUOC 1] Khach hang them \"" + sp.getTitle() + "\" them 99 cai (ton kho chi con "
                + sp.getStock() + ")");
        try {
            ctrl.themVaoGio(kh, sp.getComicId(), 99);
        } catch (IllegalArgumentException e) {
            System.out.println("  => " + e.getMessage() + " (E2)");
        } catch (Exception e) {
            System.out.println("  => Loi: " + e.getMessage());
        }
    }

    // Exception Flow E3 - Gio hang trong
    static void scenarioF(CartController ctrl, Customer kh) {
        System.out.println("\n========== GIO HANG TRONG ==========");

        System.out.println("[BUOC 1] Khach hang xoa toan bo gio hang");
        ctrl.xoaToanBo(kh);
        System.out.println("  => Da xoa toan bo.");

        System.out.println("[BUOC 2] Khach hang truy cap trang gio hang");
        Cart gio = ctrl.xemGioHang(kh);
        if (gio.isEmpty()) {
            System.out.println("  => Gio hang trong. Hay kham pha san pham! (E3)");
        } else {
            inGioHang(gio);
        }
    }

    static void inGioHang(Cart gio) {
        if (gio.isEmpty()) {
            System.out.println("  [Gio hang dang trong]");
            return;
        }
        System.out.println("  +----+---------------------------+----------+----------+--------------+");
        System.out.println("  | ID | San pham                  | Don gia  | So luong | Thanh tien   |");
        System.out.println("  +----+---------------------------+----------+----------+--------------+");
        for (CartItem item : gio.getItems()) {
            System.out.printf("  | %-2d | %-25s | %6.0f d | %8d | %10.0f d |%n",
                    item.getComicBook().getComicId(),
                    item.getComicBook().getTitle(),
                    item.getComicBook().getPrice(),
                    item.getQuantity(),
                    item.getSubTotal()
            );
        }
        System.out.println("  +----+---------------------------+----------+----------+--------------+");
        System.out.printf("  Tong tien: %,.0f dong%n", gio.getTotalAmount());
    }
}