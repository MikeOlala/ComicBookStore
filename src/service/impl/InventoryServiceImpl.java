package service.impl;

import database.FakeDatabase;
import model.*;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import service.InventoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * InventoryServiceImpl
 * ----------------------------------------------------------------------------
 * Sequence Diagram steps:
 *   [OrderService -> InventoryService]  8. verifyStock(orderID)
 *     InventoryService -> DB: query stock for each OrderItem
 *     DB --> InventoryService: return stock info
 *   InventoryService --> OrderService: return StockResult
 *
 *   [OrderService -> InventoryService]  reserveStock(orderID)
 *     InventoryService -> DB: decreaseStock for each item
 *
 *   [OrderService -> InventoryService]  6f. releaseStock(orderID)
 *     InventoryService -> DB: increaseStock for each item
 * ============================================================================
 */
public class InventoryServiceImpl implements InventoryService {

    private final OrderRepository orderRepository;

    public InventoryServiceImpl() {
        this.orderRepository = new OrderRepositoryImpl();
    }

    /* Sequence 8: Verify stock availability for each item in the order */
    @Override
    public StockResult verifyStock(int orderID) {
        System.out.println("[INVENTORY] Verifying stock for order #" + orderID);
        Order order = orderRepository.findById(orderID);
        if (order == null) {
            return new StockResult(false, List.of("Order not found"), "Order not found.");
        }

        List<String> outOfStock = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            if (!item.getComicBook().isAvailable(item.getQuantity())) {
                outOfStock.add(item.getComicBook().getTitle());
            }
        }

        if (outOfStock.isEmpty()) {
            return new StockResult(true, outOfStock, "All items in stock.");
        }
        return new StockResult(false, outOfStock, "Some items are out of stock: " + String.join(", ", outOfStock));
    }

    /* Classify inventory status based on verification result */
    @Override
    public InventoryStatus checkInventory(int orderID) {
        StockResult result = verifyStock(orderID);
        if (result.isAvailable()) return InventoryStatus.IN_STOCK;
        if (result.getOutOfStockItems().size() < 3) return InventoryStatus.LOW_STOCK;
        return InventoryStatus.OUT_OF_STOCK;
    }

    /* Sequence 8a: Reserve stock by decreasing comic book quantities */
    @Override
    public boolean reserveStock(int orderID) {
        System.out.println("[INVENTORY] Reserving stock for order #" + orderID);
        Order order = orderRepository.findById(orderID);
        if (order == null) return false;

        for (OrderItem item : order.getOrderItems()) {
            item.getComicBook().decreaseStock(item.getQuantity());
        }
        return true;
    }

    /* Sequence 6f: Release stock back when order is cancelled */
    @Override
    public void releaseStock(int orderID) {
        System.out.println("[INVENTORY] Releasing stock for order #" + orderID);
        Order order = orderRepository.findById(orderID);
        if (order == null) return;

        for (OrderItem item : order.getOrderItems()) {
            item.getComicBook().setStock(
                    item.getComicBook().getStock() + item.getQuantity()
            );
        }
    }
}
