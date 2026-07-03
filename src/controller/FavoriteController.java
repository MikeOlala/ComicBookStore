package controller;

import database.FakeDatabase;
import exception.ValidationException;
import model.Favorite;
import model.User;
import repository.FavoriteDAO;

/**
 * ============================================================================
 * Controller: FavoriteController
 * ----------------------------------------------------------------------------
 * Điều phối các yêu cầu Theo dõi/Yêu thích truyện sử dụng trực tiếp FavoriteDAO.
 * ============================================================================
 */
public class FavoriteController {

    private final FavoriteDAO favoriteDAO;

    public FavoriteController() {
        this.favoriteDAO = new FavoriteDAO();
    }

    /**
     * Thêm truyện vào danh sách yêu thích/theo dõi.
     */
    public Favorite addFavorite(int userId, int comicId) throws ValidationException {
        
        // 1. Kiểm tra đăng nhập (E1) - ném lỗi ValidationException chứa thông báo 401
        if (!checkLogin(userId)) {
            throw new ValidationException("401 Unauthorized: User must be logged in.");
        }

        // 2. Validate dữ liệu đầu vào
        validateFavorite(userId, comicId);

        // 3. Kiểm tra xem truyện đã được theo dõi chưa (E2)
        if (favoriteDAO.checkExists(userId, comicId)) {
            throw new ValidationException("E2: ComicBook is already in your favorite list.");
        }

        // 4. Tạo bản ghi theo dõi mới
        return favoriteDAO.createFavorite(userId, comicId);
    }

    /**
     * Kiểm tra trạng thái đăng nhập của người dùng.
     */
    public boolean checkLogin(int userId) {
        for (User u : FakeDatabase.USERS) {
            if (u.getUserId() == userId) {
                return u.isLoggedIn();
            }
        }
        return false;
    }

    /**
     * Xác thực dữ liệu yêu cầu theo dõi.
     */
    public void validateFavorite(int userId, int comicId) throws ValidationException {
        if (userId <= 0) {
            throw new ValidationException("Invalid User ID.");
        }
        if (comicId <= 0) {
            throw new ValidationException("Invalid ComicBook ID.");
        }
    }
}
