package wildbakery.ufu.Model.DAO;

import android.content.Intent;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;

/**
 * Created by Tatiana on 28/04/2017.
 */

public class NewsDAOImpl extends BaseDaoImpl<NewsItem, Integer> implements NewsDAO{
    public NewsDAOImpl(ConnectionSource connectionSource, Class<NewsItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }


    @Override
    public List<NewsItem> getAllNews() throws SQLException {
        return queryForAll();
    }

    @Override
    public List<NewsItem> getBatch(int startId, int count) {
        return null;
    }

    @Override
    public void insert(NewsItem item) throws SQLException {
        this.create(item);
    }

    @Override
    public void insert(Collection<NewsItem> items) throws SQLException {
        this.create(items);

    }

    @Override
    public void deleteNews(NewsItem item) {

    }

    @Override
    public void updateNews(NewsItem item) {

    }
}
