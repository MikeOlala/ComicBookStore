package service;

import model.InventoryStatus;
import model.StockResult;

public interface InventoryService {
    StockResult verifyStock(int orderID);
    InventoryStatus checkInventory(int orderID);
    boolean reserveStock(int orderID);
    void releaseStock(int orderID);
}
