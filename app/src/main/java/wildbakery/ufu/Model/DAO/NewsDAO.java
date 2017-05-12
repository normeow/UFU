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
    List<NewsItem> getBatch(long start, long limit) throws SQLException;
    void insertNews(NewsItem item) throws SQLException;
    void insertNews(Collection<NewsItem> items) throws SQLException;
    void deleteNews(NewsItem item) throws SQLException;
    // update all fields but id
    void updateNews(NewsItem item) throws SQLException;
    void deleteAllNews() throws SQLException;
}
