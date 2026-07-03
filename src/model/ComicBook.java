package model;

/**
 * ============================================================================
 * ComicBook
 *
 * Quan hệ
 *
 * Author
 * Category
 * Promotion
 *
 * ============================================================================
 */
public class ComicBook {

    private int comicId;

    private String title;

    private double price;

    private int stock;

    private Author author;

    private Category category;

    private Promotion promotion;

    public ComicBook() {
    }

    /**
     * Giá sau khuyến mãi.
     */
    public double getSalePrice() {

        if (promotion == null) {

            return price;

        }

        return price - (price * promotion.getDiscount());

    }

    /**
     * Kiểm tra tồn kho.
     */
    public boolean isAvailable(int quantity) {

        return stock >= quantity;

    }

    /**
     * Giảm tồn kho.
     */
    public void decreaseStock(int quantity) {

        stock -= quantity;

    }

    // Getter và Setter sẽ được bổ sung đầy đủ ở các bước tiếp theo

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}