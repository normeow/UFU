package wildbakery.ufu.Model.DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.SaleItem;

/**
 * Created by Tatiana on 14/05/2017.
 */

public interface SalesDAO {
    List<SaleItem> getAllSales() throws SQLException;
    void insertSale(SaleItem item) throws SQLException;
    void insertSales(Collection<SaleItem> items) throws SQLException;
    void deleteSale(SaleItem item) throws SQLException;
    void updateSale(SaleItem item) throws SQLException;
    void deleteAllSales() throws SQLException;
}
