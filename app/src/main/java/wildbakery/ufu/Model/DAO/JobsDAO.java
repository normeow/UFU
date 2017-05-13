package wildbakery.ufu.Model.DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.JobItem;

/**
 * Created by Tatiana on 13/05/2017.
 */

public interface JobsDAO {
    List<JobItem> getAllJobs() throws SQLException;
    void insertJob(JobItem item) throws SQLException;
    void insertJobs(Collection<JobItem> items) throws SQLException;
    void deleteJob(JobItem item) throws SQLException;
    void updateJob(JobItem item) throws SQLException;
    void deleteAllJobs() throws SQLException;
}
