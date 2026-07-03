package controller;

import model.Order;
import model.ReturnRequest;
import service.OrderHistoryService;

import java.util.List;

public class OrderHistoryController {

    private OrderHistoryService service;

    public OrderHistoryController(OrderHistoryService service) {
        this.service = service;
    }

    public List<Order> xemDanhSach(int userId) {
        return service.getDanhSachDonHang(userId);
    }

    public Order xemChiTiet(int orderId, int userId) {
        return service.getChiTietDonHang(orderId, userId);
    }

    public boolean muaLai(int orderId, int userId) {
        return service.muaLai(orderId, userId);
    }

    public ReturnRequest doiTra(int orderId, int userId, String lyDo) {
        return service.yeuCauDoiTra(orderId, userId, lyDo);
    }
}