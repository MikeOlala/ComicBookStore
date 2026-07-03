package service;

import dto.OrderRequest;
import exception.DatabaseException;
import exception.InvalidOrderException;
import exception.OutOfStockException;
import model.Order;

/**
 * ============================================================================
 * Interface: OrderService
 * ----------------------------------------------------------------------------
 * Use Case:
 *      UC_5 Place Order
 *
 * Chịu trách nhiệm xử lý nghiệp vụ đặt hàng.
 * ============================================================================
 */
public interface OrderService {

    /**
     * Đặt hàng.
     *
     * Basic Flow
     * BF5.7 Validate dữ liệu
     * BF5.8 Kiểm tra tồn kho
     * BF5.9 Tạo Order
     * BF5.10 Lưu Order
     * BF5.11 Cập nhật tồn kho
     * BF5.12 Thông báo thành công
     *
     * @param request OrderRequest
     * @return Order
     */
    Order placeOrder(OrderRequest request)
            throws InvalidOrderException,
            OutOfStockException,
            DatabaseException;

}