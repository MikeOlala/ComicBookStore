package service.impl;

import model.Cart;
import model.CartItem;
import model.ComicBook;
import model.Customer;
import service.CartService;

import java.util.List;

/**
 * UC_GH - Quan ly gio hang
 *
 * Basic Flow:
 *   BF1. Khach hang chon truyen, nhan "Them vao gio hang"
 *   BF2. He thong kiem tra ton kho, them vao gio
 *   BF3. Xem danh sach san pham trong gio, tinh tong tien
 *   BF4. Chinh so luong hoac xoa san pham
 *
 * Alternative Flow:
 *   A1: SP da co trong gio -> tang so luong thay vi them moi (xu ly trong Cart.addItem())
 *
 * Exception Flow:
 *   E1: SP het hang -> thong bao, khong them
 *   E2: So luong vuot ton kho -> thong bao so luong con lai
 *   E3: Gio hang trong -> thong bao
 */
public class CartServiceImpl implements CartService {

    private List<ComicBook> danhSachSanPham;

    public CartServiceImpl(List<ComicBook> danhSachSanPham) {
        this.danhSachSanPham = danhSachSanPham;
    }

    private Cart layHoacTaoGioHang(Customer customer) {
        if (customer.getCart() == null) {
            customer.setCart(new Cart());
        }
        return customer.getCart();
    }

    private ComicBook timSanPham(int comicId) {
        for (ComicBook sach : danhSachSanPham) {
            if (sach.getComicId() == comicId) {
                return sach;
            }
        }
        return null;
    }

    /**
     * BF1 + BF2 - Them san pham vao gio hang.
     * E1: Het hang -> nem IllegalStateException
     * E2: Vuot ton kho -> nem IllegalArgumentException
     * A1: Da co trong gio -> Cart.addItem() tu tang so luong
     */
    @Override
    public void themVaoGio(Customer customer, int comicId, int soLuong) {
        ComicBook sach = timSanPham(comicId);

        if (sach == null) {
            throw new IllegalArgumentException("Khong tim thay san pham #" + comicId);
        }

        // E1: Het hang
        if (sach.getStock() <= 0) {
            throw new IllegalStateException("San pham \"" + sach.getTitle() + "\" da het hang.");
        }

        // E2: Vuot ton kho
        Cart gioHang = layHoacTaoGioHang(customer);
        int soLuongTrongGio = 0;
        for (CartItem item : gioHang.getItems()) {
            if (item.getComicBook().getComicId() == comicId) {
                soLuongTrongGio = item.getQuantity();
                break;
            }
        }
        if (soLuongTrongGio + soLuong > sach.getStock()) {
            throw new IllegalArgumentException(
                    "Chi con " + (sach.getStock() - soLuongTrongGio) + " san pham trong kho."
            );
        }

        // A1: Da co trong gio thi Cart.addItem() tu xu ly tang so luong
        gioHang.addItem(sach, soLuong);
    }

    /**
     * BF3 - Xem gio hang.
     * E3: Gio trong -> tra ve cart rong (xu ly o tang hien thi)
     */
    @Override
    public Cart xemGioHang(Customer customer) {
        return layHoacTaoGioHang(customer);
    }

    /**
     * BF4 - Cap nhat so luong san pham.
     */
    @Override
    public void capNhatSoLuong(Customer customer, int comicId, int soLuongMoi) {
        Cart gioHang = layHoacTaoGioHang(customer);

        if (soLuongMoi <= 0) {
            gioHang.removeItem(comicId);
            return;
        }

        // Kiem tra ton kho truoc khi cap nhat
        ComicBook sach = timSanPham(comicId);
        if (sach != null && soLuongMoi > sach.getStock()) {
            throw new IllegalArgumentException(
                    "Chi con " + sach.getStock() + " san pham trong kho."
            );
        }

        for (CartItem item : gioHang.getItems()) {
            if (item.getComicBook().getComicId() == comicId) {
                item.setQuantity(soLuongMoi);
                return;
            }
        }
    }

    /**
     * BF4 - Xoa mot san pham khoi gio.
     */
    @Override
    public void xoaSanPham(Customer customer, int comicId) {
        layHoacTaoGioHang(customer).removeItem(comicId);
    }

    /**
     * Xoa toan bo gio hang.
     */
    @Override
    public void xoaToanBo(Customer customer) {
        layHoacTaoGioHang(customer).clear();
    }
}