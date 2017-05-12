package wildbakery.ufu.Model.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import org.jsoup.Connection;

import java.sql.SQLException;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.Image;

/**
 * Created by Tatiana on 12/05/2017.
 */

public class ImageDAOImpl extends BaseDaoImpl<Image, Integer> implements ImageDAO {

    public ImageDAOImpl(ConnectionSource connectionSource, Class<Image> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public List<Image> getImages() {
        return null;
    }

    @Override
    public void insertImages(List<Image> images) throws SQLException {
        for(Image image : images)
            insertImage(image);
    }

    @Override
    public void insertImage(Image image) throws SQLException {
        createOrUpdate(image);
    }

    @Override
    public void deleteImage(Image image) {

    }

    @Override
    public void deleteAllImages() {

    }

    @Override
    public int refreshImage(Image data) throws SQLException {
        return super.refresh(data);
    }
}
