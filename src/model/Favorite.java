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
    private Story story;

    public Favorite() {
        this.createdAt = new Date();
    }

    public Favorite(int favoriteId, User user, Story story) {
        this.favoriteId = favoriteId;
        this.user = user;
        this.story = story;
        this.createdAt = new Date();
    }

    /**
     * Thực hiện thêm theo dõi (giả lập liên kết logic).
     */
    public void addFavorite() {
        System.out.println("Favorite relation established between User: " 
                + (user != null ? user.getEmail() : "null") 
                + " and Story: " + (story != null ? story.getTitle() : "null"));
    }

    /**
     * Thực hiện xóa theo dõi.
     */
    public void removeFavorite() {
        System.out.println("Favorite relation removed between User: " 
                + (user != null ? user.getEmail() : "null") 
                + " and Story: " + (story != null ? story.getTitle() : "null"));
        this.user = null;
        this.story = null;
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

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
