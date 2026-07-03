package model;

/**
 * ============================================================================
 * Class: CartItem
 * ----------------------------------------------------------------------------
 * Mô tả:
 * Đại diện cho một sản phẩm trong giỏ hàng.
 *
 * Quan hệ:
 *      Cart 1 -------- * CartItem
 *      ComicBook 1 --- * CartItem
 *
 * Use Case:
 *      UC_5 Place Order
 * ============================================================================
 */
public class CartItem {

    /**
     * Truyện tranh.
     */
    private ComicBook comicBook;

    /**
     * Số lượng.
     */
    private int quantity;

    public CartItem() {
    }

    public CartItem(ComicBook comicBook, int quantity) {
        this.comicBook = comicBook;
        this.quantity = quantity;
    }

    /**
     * Thành tiền của sản phẩm.
     */
    public double getSubTotal() {
        return comicBook.getSalePrice() * quantity;
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
}