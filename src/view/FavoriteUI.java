package view;

import model.Story;

/**
 * ============================================================================
 * Class: FavoriteUI
 * ----------------------------------------------------------------------------
 * Giao diện hiển thị nút theo dõi và trạng thái theo dõi của truyện.
 * ============================================================================
 */
public class FavoriteUI {

    public void showFollowButton(Story story) {
        if (story != null) {
            System.out.println("[UI - Favorite Button] Displaying 'Follow' button for story: \"" 
                    + story.getTitle() + "\"");
        }
    }

    public void updateFollowStatus(boolean followed) {
        if (followed) {
            System.out.println("[UI - Favorite Button Status] Updated to: [Đã theo dõi] (Green)");
        } else {
            System.out.println("[UI - Favorite Button Status] Updated to: [Theo dõi bộ truyện] (Gray)");
        }
    }

    public void showSuccessMessage(String message) {
        System.out.println("[UI SUCCESS] " + message);
    }

    public void showWarningMessage(String message) {
        System.out.println("[UI WARNING] " + message);
    }

    public void redirectLogin() {
        System.out.println("[UI ACTION] User is not logged in. Redirecting to Login page...");
    }
}
