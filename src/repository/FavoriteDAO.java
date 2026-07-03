package repository;

import database.FakeDatabase;
import model.Favorite;
import model.Story;
import model.User;

/**
 * ============================================================================
 * Class: FavoriteDAO
 * ----------------------------------------------------------------------------
 * Quản lý dữ liệu theo dõi truyện (Favorites) trực tiếp từ Database.
 * ============================================================================
 */
public class FavoriteDAO {

    public boolean checkExists(int userId, int storyId) {
        for (Favorite fav : FakeDatabase.FAVORITES) {
            if (fav.getUser() != null && fav.getUser().getUserId() == userId
                    && fav.getStory() != null && fav.getStory().getStoryId() == storyId) {
                return true;
            }
        }
        return false;
    }

    public Favorite createFavorite(int userId, int storyId) {
        User user = null;
        for (User u : FakeDatabase.USERS) {
            if (u.getUserId() == userId) {
                user = u;
                break;
            }
        }

        Story story = null;
        for (Story s : FakeDatabase.STORIES) {
            if (s.getStoryId() == storyId) {
                story = s;
                break;
            }
        }

        if (user == null || story == null) {
            return null;
        }

        int nextFavId = 1;
        for (Favorite f : FakeDatabase.FAVORITES) {
            if (f.getFavoriteId() >= nextFavId) {
                nextFavId = f.getFavoriteId() + 1;
            }
        }

        Favorite favorite = new Favorite(nextFavId, user, story);
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
