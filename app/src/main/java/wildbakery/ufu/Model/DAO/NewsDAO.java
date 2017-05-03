package wildbakery.ufu.Model.DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;

/**
 * Created by Tatiana on 28/04/2017.
 */

public interface NewsDAO {
    List<NewsItem> getAllNews() throws SQLException;
    List<NewsItem> getBatch(int startId, int count);
    void insertNews(NewsItem item) throws SQLException;
    void insertNews(Collection<NewsItem> items) throws SQLException;
    void deleteNews(NewsItem item);
    // update all fields but id
    void updateNews(NewsItem item);
}
