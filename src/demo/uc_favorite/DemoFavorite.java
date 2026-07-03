package demo.uc_favorite;

import controller.FavoriteController;
import controller.RegisterController;
import database.FakeDatabase;
import dto.RegisterRequest;
import exception.ValidationException;
import model.Customer;
import model.Favorite;
import model.ComicBook;
import view.FavoriteUI;

public class DemoFavorite {

    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println("     DEMO: FOLLOW & FAVORITE COMIC BOOK FLOW      ");
            System.out.println("==================================================");

            // 1. Setup customer and comic book
            RegisterController registerController = new RegisterController();
            RegisterRequest registerRequest = new RegisterRequest(
                    "User Test",
                    "usertest@gmail.com",
                    "password123",
                    "password123"
            );
            Customer customer = registerController.register(registerRequest);
            System.out.println("Registered User: ID=" + customer.getUserId() + ", Email=" + customer.getEmail());

            ComicBook comicBook = FakeDatabase.COMIC_BOOKS.get(0);
            System.out.println("Available ComicBook: ID=" + comicBook.getComicId() + ", Title=\"" + comicBook.getTitle() + "\"");

            FavoriteController favoriteController = new FavoriteController();
            FavoriteUI favoriteUI = new FavoriteUI();

            // Display UI button state initially
            favoriteUI.showFollowButton(comicBook);
            favoriteUI.updateFollowStatus(false);

            // ========================================================================
            // SCENARIO 1: Follow comic book when not logged in (E1: 401 Unauthorized)
            // ========================================================================
            System.out.println("\n--- [Scenario 1: Not Logged In (E1 Flow)] ---");
            System.out.println("Attempting to follow comic book (ID=" + comicBook.getComicId() + ") while logged out...");
            try {
                favoriteController.addFavorite(customer.getUserId(), comicBook.getComicId());
            } catch (ValidationException ex) {
                System.out.println("Denied (Expected): " + ex.getMessage());
                favoriteUI.redirectLogin();
            }

            // ========================================================================
            // SCENARIO 2: Follow comic book when logged in (Basic Flow)
            // ========================================================================
            System.out.println("\n--- [Scenario 2: Logged In (Basic Flow)] ---");
            System.out.println("Logging in user...");
            customer.login();
            System.out.println("User login status: " + customer.isLoggedIn());

            System.out.println("Attempting to follow comic book (ID=" + comicBook.getComicId() + ") now...");
            Favorite favorite = favoriteController.addFavorite(customer.getUserId(), comicBook.getComicId());
            favoriteUI.showSuccessMessage("Favorite Record Created successfully!");
            System.out.println(" - Favorite ID: " + favorite.getFavoriteId());
            System.out.println(" - User Email: " + favorite.getUser().getEmail());
            System.out.println(" - ComicBook Title: " + favorite.getComicBook().getTitle());
            System.out.println(" - Created At: " + favorite.getCreatedAt());
            favoriteUI.updateFollowStatus(true);

            // ========================================================================
            // SCENARIO 3: Follow already followed comic book (E2 Warning)
            // ========================================================================
            System.out.println("\n--- [Scenario 3: Already Followed (E2 Flow)] ---");
            System.out.println("Attempting to follow the same comic book (ID=" + comicBook.getComicId() + ") again...");
            try {
                favoriteController.addFavorite(customer.getUserId(), comicBook.getComicId());
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
