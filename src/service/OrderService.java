package service;

import dto.*;
import exception.DatabaseException;
import exception.InvalidOrderException;
import exception.OutOfStockException;
import model.*;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Order placeOrder(OrderRequest request)
            throws InvalidOrderException, OutOfStockException, DatabaseException;

    List<Order> getOrders(Map<String, String> filters);

    Order getOrderDetails(int orderID);

    Order processApproval(int orderID) throws Exception;

    Order processCancellation(int orderID, String reason) throws Exception;

    TrackingInfo processAutoAssignment(int orderID);

    TrackingInfo processAssignment(int orderID, long shipperID);

    void processUpdateInfo(int orderID, Map<String, String> info);

    void processRefund(int orderID);

    void updateStatus(int orderID, String status);

    String generateTrackingCode();

    String regenerateTrackingCode();

    // DTO-returning methods (from drawio)
    ApprovalResult approveOrder(int orderID);
    CancellationResult cancelOrder(int orderID, String reason);
    AssignmentResult assignShipper(int orderID, long shipperID);
    AssignmentResult autoAssignShipper(int orderID);
    RefundResult refundOrder(int orderID);
    OrderDetails getOrderDetailsDTO(int orderID);
    ValidationResult validateOrderForCancellation(int orderID);
}
