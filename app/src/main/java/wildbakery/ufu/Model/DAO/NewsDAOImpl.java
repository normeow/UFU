package wildbakery.ufu.Model.DAO;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.DatabaseHelper;
import wildbakery.ufu.Model.HelperFactory;

/**
 * Created by Tatiana on 28/04/2017.
 */

public class NewsDAOImpl extends BaseDaoImpl<NewsItem, Integer> implements NewsDAO{
    private ImageDAO imageDAO;
    public NewsDAOImpl(ConnectionSource connectionSource, Class<NewsItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public List<NewsItem> getAllNews() throws SQLException {
        imageDAO = HelperFactory.getHelper().getImageDAO();
        List<NewsItem> items = queryBuilder().orderBy(Constants.TABLES.COLUMN_PK_ID, true).query();
        for (NewsItem item : items){
            if (item.getImage() != null){
                imageDAO.refreshImage(item.getImage());
            }
        }
        return items;
    }

    @Override
    public List<NewsItem> getBatch(long start, long limit) throws SQLException {
        List<NewsItem> items = queryBuilder().orderBy(Constants.TABLES.COLUMN_PK_ID, true).offset(start).limit(limit).query();
        return null;
    }

    @Override
    public void insertNews(NewsItem item) throws SQLException {
        if (item.getImage() != null){
            imageDAO.insertImage(item.getImage());
        }
        this.createOrUpdate(item);
    }

    @Override
    public void insertNews(Collection<NewsItem> items) throws SQLException {
        for (NewsItem item : items){
            insertNews(item);
        }

    }

    @Override
    public void deleteNews(NewsItem item) throws SQLException {
        delete(item);
    }

    @Override
    public void updateNews(NewsItem item) throws SQLException {
        update(item);
    }

    @Override
    public void deleteAllNews() throws SQLException {
        Log.d(getClass().getCanonicalName(), "clear table News");
        TableUtils.clearTable(getConnectionSource(), getDataClass());
    }
}
