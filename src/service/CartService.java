package service;

import model.Cart;
import model.Customer;

/**
 * UC_GH - Quan ly gio hang
 */
public interface CartService {

    // Them san pham vao gio hang
    void themVaoGio(Customer customer, int comicId, int soLuong);

    // Xem gio hang
    Cart xemGioHang(Customer customer);

    // Cap nhat so luong
    void capNhatSoLuong(Customer customer, int comicId, int soLuongMoi);

    // Xoa san pham khoi gio
    void xoaSanPham(Customer customer, int comicId);

    // Xoa toan bo gio hang
    void xoaToanBo(Customer customer);
}