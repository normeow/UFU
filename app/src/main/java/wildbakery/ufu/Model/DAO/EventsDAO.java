package wildbakery.ufu.Model.DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.EventItem;

/**
 * Created by Tatiana on 14/05/2017.
 */

public interface EventsDAO {
    List<EventItem> getAllEvents() throws SQLException;
    void insertEvent(EventItem item) throws SQLException;
    void insertEvents(Collection<EventItem> items) throws SQLException;
    void deleteEvent(EventItem item) throws SQLException;
    void updateEvent(EventItem item) throws SQLException;
    void deleteAllEvents() throws SQLException;
}
