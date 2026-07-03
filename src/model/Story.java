package model;

/**
 * ============================================================================
 * Class: Story
 * ----------------------------------------------------------------------------
 * Quản lý thông tin bộ truyện.
 * ============================================================================
 */
public class Story {

    private int storyId;
    private String title;
    private String author;
    private String status;

    public Story() {
    }

    public Story(int storyId, String title, String author, String status) {
        this.storyId = storyId;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public String viewDetail() {
        return "Story Detail: [ID: " + storyId + ", Title: " + title 
                + ", Author: " + author + ", Status: " + status + "]";
    }

    // Getters and Setters

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
