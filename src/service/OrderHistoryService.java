package service;

import model.Order;
import model.ReturnRequest;

import java.util.List;

public interface OrderHistoryService {

    List<Order> getDanhSachDonHang(int userId);

    Order getChiTietDonHang(int orderId, int userId);

    boolean muaLai(int orderId, int userId);

    ReturnRequest yeuCauDoiTra(int orderId, int userId, String lyDo);

}