package wildbakery.ufu.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.DAO.NewsDAO;
import wildbakery.ufu.Model.DAO.NewsDAOImpl;

/**
 * Created by Tatiana on 28/04/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ufu.db";

    private NewsDAO newsDao;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, NewsItem.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, NewsItem.class, true);
            onCreate(sqLiteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
}
    }

    public NewsDAO getNewsDao() throws SQLException {
        if (newsDao == null)
            newsDao = new NewsDAOImpl(getConnectionSource(), NewsItem.class);
        Log.v(getClass().getCanonicalName(), "got newsDao");
        return newsDao;
    }

}
