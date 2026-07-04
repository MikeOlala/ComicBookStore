package controller;

import model.Cart;
import model.Customer;
import service.CartService;

/**
 * UC_GH - Quan ly gio hang
 * Nhan request tu nguoi dung, goi service xu ly.
 */
public class CartController {

    private CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    /**
     * Them san pham vao gio hang.
     */
    public void themVaoGio(Customer customer, int comicId, int soLuong) {
        service.themVaoGio(customer, comicId, soLuong);
    }

    /**
     * Lay gio hang de hien thi.
     */
    public Cart xemGioHang(Customer customer) {
        return service.xemGioHang(customer);
    }

    /**
     * Cap nhat so luong (soLuong = 0 se xoa san pham).
     */
    public void capNhatSoLuong(Customer customer, int comicId, int soLuong) {
        service.capNhatSoLuong(customer, comicId, soLuong);
    }

    /**
     * Xoa mot san pham khoi gio.
     */
    public void xoaSanPham(Customer customer, int comicId) {
        service.xoaSanPham(customer, comicId);
    }

    /**
     * Xoa toan bo gio hang.
     */
    public void xoaToanBo(Customer customer) {
        service.xoaToanBo(customer);
    }
}