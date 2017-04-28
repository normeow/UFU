package wildbakery.ufu.Model.DAO;

import android.content.Intent;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;

/**
 * Created by Tatiana on 28/04/2017.
 */

public class NewsDAO extends BaseDaoImpl<NewsItem, Integer> {
    protected NewsDAO(ConnectionSource connectionSource, Class<NewsItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<NewsItem> getAllNews() throws SQLException {
        return queryForAll();
    }

    public List<NewsItem> getBatch(int startId, int count){
        return null;
    }
}
