package demo.uc_favorite;

import controller.FavoriteController;
import controller.RegisterController;
import database.FakeDatabase;
import dto.RegisterRequest;
import exception.ValidationException;
import model.Customer;
import model.Favorite;
import model.Story;
import view.FavoriteUI;

public class DemoFavorite {

    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println("         DEMO: FOLLOW & FAVORITE STORY FLOW        ");
            System.out.println("==================================================");

            // 1. Setup customer and story
            RegisterController registerController = new RegisterController();
            RegisterRequest registerRequest = new RegisterRequest(
                    "User Test",
                    "usertest@gmail.com",
                    "password123",
                    "password123"
            );
            Customer customer = registerController.register(registerRequest);
            System.out.println("Registered User: ID=" + customer.getUserId() + ", Email=" + customer.getEmail());

            Story story = new Story(1001, "Doraemon", "Fujiko F. Fujio", "COMPLETED");
            FakeDatabase.STORIES.add(story);
            System.out.println("Available Story: ID=" + story.getStoryId() + ", Title=\"" + story.getTitle() + "\"");

            FavoriteController favoriteController = new FavoriteController();
            FavoriteUI favoriteUI = new FavoriteUI();

            // Display UI button state initially
            favoriteUI.showFollowButton(story);
            favoriteUI.updateFollowStatus(false);

            // ========================================================================
            // SCENARIO 1: Follow story when not logged in (E1: 401 Unauthorized)
            // ========================================================================
            System.out.println("\n--- [Scenario 1: Not Logged In (E1 Flow)] ---");
            System.out.println("Attempting to follow story (ID=1001) while logged out...");
            try {
                favoriteController.addFavorite(customer.getUserId(), story.getStoryId());
            } catch (ValidationException ex) {
                System.out.println("Denied (Expected): " + ex.getMessage());
                favoriteUI.redirectLogin();
            }

            // ========================================================================
            // SCENARIO 2: Follow story when logged in (Basic Flow)
            // ========================================================================
            System.out.println("\n--- [Scenario 2: Logged In (Basic Flow)] ---");
            System.out.println("Logging in user...");
            customer.login();
            System.out.println("User login status: " + customer.isLoggedIn());

            System.out.println("Attempting to follow story (ID=1001) now...");
            Favorite favorite = favoriteController.addFavorite(customer.getUserId(), story.getStoryId());
            favoriteUI.showSuccessMessage("Favorite Record Created successfully!");
            System.out.println(" - Favorite ID: " + favorite.getFavoriteId());
            System.out.println(" - User Email: " + favorite.getUser().getEmail());
            System.out.println(" - Story Title: " + favorite.getStory().getTitle());
            System.out.println(" - Created At: " + favorite.getCreatedAt());
            favoriteUI.updateFollowStatus(true);

            // ========================================================================
            // SCENARIO 3: Follow already followed story (E2 Warning)
            // ========================================================================
            System.out.println("\n--- [Scenario 3: Already Followed (E2 Flow)] ---");
            System.out.println("Attempting to follow the same story (ID=1001) again...");
            try {
                favoriteController.addFavorite(customer.getUserId(), story.getStoryId());
            } catch (ValidationException ex) {
                favoriteUI.showWarningMessage("Warning/Denied (Expected): " + ex.getMessage());
            }

            System.out.println("\n==================================================");
            System.out.println("         DEMO SUCCESSFULLY COMPLETED               ");
            System.out.println("==================================================");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
