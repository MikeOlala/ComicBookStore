package model;

import java.util.Date;

/**
 * ============================================================================
 * Class: Favorite
 * ----------------------------------------------------------------------------
 * Quản lý thông tin theo dõi bộ truyện (Favorites).
 * ============================================================================
 */
public class Favorite {

    private int favoriteId;
    private Date createdAt;
    private User user;
    private ComicBook comicBook;

    public Favorite() {
        this.createdAt = new Date();
    }

    public Favorite(int favoriteId, User user, ComicBook comicBook) {
        this.favoriteId = favoriteId;
        this.user = user;
        this.comicBook = comicBook;
        this.createdAt = new Date();
    }

    /**
     * Thực hiện thêm theo dõi (giả lập liên kết logic).
     */
    public void addFavorite() {
        System.out.println("Favorite relation established between User: " 
                + (user != null ? user.getEmail() : "null") 
                + " and ComicBook: " + (comicBook != null ? comicBook.getTitle() : "null"));
    }

    /**
     * Thực hiện xóa theo dõi.
     */
    public void removeFavorite() {
        System.out.println("Favorite relation removed between User: " 
                + (user != null ? user.getEmail() : "null") 
                + " and ComicBook: " + (comicBook != null ? comicBook.getTitle() : "null"));
        this.user = null;
        this.comicBook = null;
    }

    // Getters and Setters

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ComicBook getComicBook() {
        return comicBook;
    }

    public void setComicBook(ComicBook comicBook) {
        this.comicBook = comicBook;
    }
}
