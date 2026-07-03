package model;

import java.util.ArrayList;
import java.util.List;

public class StockResult {
    private boolean available;
    private List<String> outOfStockItems;
    private String message;

    public StockResult() {
        this.outOfStockItems = new ArrayList<>();
    }

    public StockResult(boolean available, List<String> outOfStockItems, String message) {
        this.available = available;
        this.outOfStockItems = outOfStockItems != null ? outOfStockItems : new ArrayList<>();
        this.message = message;
    }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public List<String> getOutOfStockItems() { return outOfStockItems; }
    public void setOutOfStockItems(List<String> outOfStockItems) { this.outOfStockItems = outOfStockItems; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
