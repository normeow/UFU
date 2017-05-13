package wildbakery.ufu.Model.DAO;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.JobItem;
import wildbakery.ufu.Model.ApiModels.NewsItem;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class JobsDAOImpl extends BaseDaoImpl<JobItem, Integer> implements JobsDAO {

    public JobsDAOImpl(ConnectionSource connectionSource, Class<JobItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public List<JobItem> getAllJobs() throws SQLException {
        return queryBuilder().orderBy(Constants.TABLES.COLUMN_PK_ID, true).query();
    }


    @Override
    public void insertJob(JobItem item) throws SQLException {
        createOrUpdate(item);
    }

    @Override
    public void insertJobs(Collection<JobItem> items) throws SQLException {
        for (JobItem item : items)
            insertJob(item);
    }

    @Override
    public void deleteJob(JobItem item) throws SQLException {
        delete(item);
    }

    @Override
    public void updateJob(JobItem item) throws SQLException {
        update(item);
    }

    @Override
    public void deleteAllJobs() throws SQLException {
        Log.d(getClass().getCanonicalName(), "clear table Jobs");
        TableUtils.clearTable(getConnectionSource(), getDataClass());
    }
}
