package wildbakery.ufu.Model.DAO;

import java.sql.SQLException;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.Image;

/**
 * Created by Tatiana on 12/05/2017.
 */

public interface ImageDAO {
    List<Image> getImages();
    void insertImages(List<Image> image) throws SQLException;
    void insertImage(Image image) throws SQLException;
    void deleteImage(Image image);
    void deleteAllImages();
    int refreshImage(Image image) throws SQLException;

}
