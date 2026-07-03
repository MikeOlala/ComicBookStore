package model;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Class: Cart
 * ----------------------------------------------------------------------------
 * Giỏ hàng của khách hàng.
 *
 * Quan hệ:
 *      Customer 1 ----- 1 Cart
 *      Cart 1 --------- * CartItem
 *
 * Use Case:
 *      UC_5 Place Order
 * ============================================================================
 */
public class Cart {

    /**
     * Danh sách sản phẩm.
     */
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    /**
     * Thêm sản phẩm vào giỏ.
     */
    public void addItem(ComicBook comicBook, int quantity) {

        for (CartItem item : items) {

            if (item.getComicBook().getComicId() == comicBook.getComicId()) {

                item.setQuantity(item.getQuantity() + quantity);

                return;
            }
        }

        items.add(new CartItem(comicBook, quantity));
    }

    /**
     * Xóa sản phẩm khỏi giỏ.
     */
    public void removeItem(int comicId) {

        items.removeIf(item ->
                item.getComicBook().getComicId() == comicId);

    }

    /**
     * Xóa toàn bộ giỏ hàng.
     */
    public void clear() {

        items.clear();

    }

    /**
     * Kiểm tra giỏ hàng rỗng.
     *
     * Alternative Flow A1.
     */
    public boolean isEmpty() {

        return items.isEmpty();

    }

    /**
     * Tổng tiền.
     */
    public double getTotalAmount() {

        double total = 0;

        for (CartItem item : items) {

            total += item.getSubTotal();

        }

        return total;

    }

    public List<CartItem> getItems() {
        return items;
    }
}