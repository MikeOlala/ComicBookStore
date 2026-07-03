package service.impl;

import model.*;
import service.OrderHistoryService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryServiceImpl implements OrderHistoryService {

    private List<ReturnRequest> danhSachDoiTra = new ArrayList<>();
    private int returnIdCounter = 1;

    private List<Order> fakeOrders;

    public OrderHistoryServiceImpl(List<Order> fakeOrders) {
        this.fakeOrders = fakeOrders;
    }

    @Override
    public List<Order> getDanhSachDonHang(int userId) {
        List<Order> ketQua = new ArrayList<>();
        for (Order o : fakeOrders) {
            if (o.getCustomer() != null && o.getCustomer().getUserId() == userId) {
                ketQua.add(o);
            }
        }
        return ketQua;
    }

    @Override
    public Order getChiTietDonHang(int orderId, int userId) {
        for (Order o : fakeOrders) {
            if (o.getOrderId() == orderId
                    && o.getCustomer() != null
                    && o.getCustomer().getUserId() == userId) {
                return o;
            }
        }
        return null;
    }

    @Override
    public boolean muaLai(int orderId, int userId) {
        Order donCu = getChiTietDonHang(orderId, userId);
        if (donCu == null) {
            return false;
        }

        // Lay gio hang hien tai, neu null thi tao moi
        Cart gioHang = donCu.getCustomer().getCart();
        if (gioHang == null) {
            gioHang = new Cart();
            donCu.getCustomer().setCart(gioHang);
        }

        for (OrderItem item : donCu.getOrderItems()) {
            ComicBook sach = item.getComicBook();

            if (sach.getStock() <= 0) {
                System.out.println("  [!] San pham \"" + sach.getTitle() + "\" da het hang, bo qua.");
                continue;
            }

            gioHang.addItem(sach, item.getQuantity());
            System.out.println("  [+] Da them: " + sach.getTitle() + " x" + item.getQuantity());
        }

        return true;
    }

    @Override
    public ReturnRequest yeuCauDoiTra(int orderId, int userId, String lyDo) {
        Order don = getChiTietDonHang(orderId, userId);
        if (don == null) {
            return null;
        }

        long soNgay = ChronoUnit.DAYS.between(don.getOrderDate(), LocalDateTime.now());
        if (soNgay > 7) {
            return null;
        }

        ReturnRequest yeuCau = new ReturnRequest(returnIdCounter++, don, lyDo);
        danhSachDoiTra.add(yeuCau);
        return yeuCau;
    }

    public List<ReturnRequest> getDanhSachDoiTra() {
        return danhSachDoiTra;
    }
}