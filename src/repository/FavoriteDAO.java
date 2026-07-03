package repository;

import database.FakeDatabase;
import model.ComicBook;
import model.Favorite;
import model.User;

/**
 * ============================================================================
 * Class: FavoriteDAO
 * ----------------------------------------------------------------------------
 * Quản lý dữ liệu theo dõi truyện (Favorites) trực tiếp từ Database.
 * ============================================================================
 */
public class FavoriteDAO {

    public boolean checkExists(int userId, int comicId) {
        for (Favorite fav : FakeDatabase.FAVORITES) {
            if (fav.getUser() != null && fav.getUser().getUserId() == userId
                    && fav.getComicBook() != null && fav.getComicBook().getComicId() == comicId) {
                return true;
            }
        }
        return false;
    }

    public Favorite createFavorite(int userId, int comicId) {
        User user = null;
        for (User u : FakeDatabase.USERS) {
            if (u.getUserId() == userId) {
                user = u;
                break;
            }
        }

        ComicBook comicBook = null;
        for (ComicBook cb : FakeDatabase.COMIC_BOOKS) {
            if (cb.getComicId() == comicId) {
                comicBook = cb;
                break;
            }
        }

        if (user == null || comicBook == null) {
            return null;
        }

        int nextFavId = 1;
        for (Favorite f : FakeDatabase.FAVORITES) {
            if (f.getFavoriteId() >= nextFavId) {
                nextFavId = f.getFavoriteId() + 1;
            }
        }

        Favorite favorite = new Favorite(nextFavId, user, comicBook);
        favorite.addFavorite(); // Thiết lập quan hệ
        FakeDatabase.FAVORITES.add(favorite);
        return favorite;
    }

    public boolean deleteFavorite(int favoriteId) {
        for (int i = 0; i < FakeDatabase.FAVORITES.size(); i++) {
            Favorite fav = FakeDatabase.FAVORITES.get(i);
            if (fav.getFavoriteId() == favoriteId) {
                fav.removeFavorite();
                FakeDatabase.FAVORITES.remove(i);
                return true;
            }
        }
        return false;
    }
}
