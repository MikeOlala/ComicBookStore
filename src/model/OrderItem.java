package model;

/**
 * ============================================================================
 * Class: OrderItem
 * ----------------------------------------------------------------------------
 * Đại diện cho một sản phẩm trong đơn hàng.
 *
 * Quan hệ:
 *      Order 1 -------- * OrderItem
 *      ComicBook 1 ---- * OrderItem
 *
 * Use Case:
 *      UC_5 Place Order
 * ============================================================================
 */
public class OrderItem {

    private ComicBook comicBook;

    private int quantity;

    /**
     * Giá tại thời điểm đặt hàng.
     */
    private double unitPrice;

    public OrderItem() {
    }

    public OrderItem(ComicBook comicBook,
                     int quantity,
                     double unitPrice) {

        this.comicBook = comicBook;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Thành tiền.
     */
    public double getSubTotal() {

        return quantity * unitPrice;

    }

    public ComicBook getComicBook() {
        return comicBook;
    }

    public void setComicBook(ComicBook comicBook) {
        this.comicBook = comicBook;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}