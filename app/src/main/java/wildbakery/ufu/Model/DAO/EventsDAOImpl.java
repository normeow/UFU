package wildbakery.ufu.Model.DAO;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.EventItem;
import wildbakery.ufu.Model.HelperFactory;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class EventsDAOImpl extends BaseDaoImpl<EventItem, Integer> implements EventsDAO {
    private ImageDAO imageDAO;
    public EventsDAOImpl(ConnectionSource connectionSource, Class<EventItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        imageDAO = HelperFactory.getHelper().getImageDAO();

    }

    @Override
    public List<EventItem> getAllEvents() throws SQLException {
        List<EventItem> items = queryBuilder().orderBy(Constants.TABLES.COLUMN_PK_ID, true).query();
        for (EventItem item : items){
            if (item.getImage() != null){
                imageDAO.refreshImage(item.getImage());
            }
        }
        return items;
    }


    @Override
    public void insertEvent(EventItem item) throws SQLException {
        if (item.getImage() != null){
            imageDAO.insertImage(item.getImage());
        }
        createOrUpdate(item);
    }

    @Override
    public void insertEvents(Collection<EventItem> items) throws SQLException {
        for (EventItem item : items)
            insertEvent(item);
    }

    @Override
    public void deleteEvent(EventItem item) throws SQLException {
        delete(item);
        if (item.getImage() != null)
            imageDAO.deleteImage(item.getImage());
    }

    @Override
    public void updateEvent(EventItem item) throws SQLException {
        update(item);
    }

    @Override
    public void deleteAllEvents() throws SQLException {
        Log.d(getClass().getCanonicalName(), "clear table Events");
        TableUtils.clearTable(getConnectionSource(), getDataClass());
    }
}
