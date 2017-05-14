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
import wildbakery.ufu.Model.ApiModels.SaleItem;
import wildbakery.ufu.Model.HelperFactory;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class SalesDAOImpl extends BaseDaoImpl<SaleItem, Integer> implements SalesDAO {
    private ImageDAO imageDAO;
    public SalesDAOImpl(ConnectionSource connectionSource, Class<SaleItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        imageDAO = HelperFactory.getHelper().getImageDAO();

    }

    @Override
    public List<SaleItem> getAllSales() throws SQLException {
        List<SaleItem> items = queryBuilder().orderBy(Constants.TABLES.COLUMN_PK_ID, true).query();
        for (SaleItem item : items){
            if (item.getImage() != null){
                imageDAO.refreshImage(item.getImage());
            }
        }
        return items;
    }


    @Override
    public void insertSale(SaleItem item) throws SQLException {
        if (item.getImage() != null){
            imageDAO.insertImage(item.getImage());
        }
        createOrUpdate(item);
    }

    @Override
    public void insertSales(Collection<SaleItem> items) throws SQLException {
        for (SaleItem item : items)
            insertSale(item);
    }

    @Override
    public void deleteSale(SaleItem item) throws SQLException {
        delete(item);
        if (item.getImage() != null)
            imageDAO.deleteImage(item.getImage());
    }

    @Override
    public void updateSale(SaleItem item) throws SQLException {
        update(item);
    }

    @Override
    public void deleteAllSales() throws SQLException {
        Log.d(getClass().getCanonicalName(), "clear table Sales");
        TableUtils.clearTable(getConnectionSource(), getDataClass());
    }
}
