
package wildbakery.ufu.Model.ApiModels;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import wildbakery.ufu.App;
import wildbakery.ufu.Constants;
import wildbakery.ufu.R;

@DatabaseTable(tableName = Constants.TABLES.TABLE_NEWS)
public class NewsItem extends Item{

    @SerializedName("news_when")
    @Expose
    @DatabaseField()
    private String newsWhen;

    @SerializedName("short_description")
    @Expose
    @DatabaseField()
    private String shortDescription;

    @SerializedName("category")
    @Expose
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Category category;

    @SerializedName("image")
    @Expose
    @DatabaseField(foreign = true)
    private Image image;

    @SerializedName("orientation_mode")
    @Expose
    @DatabaseField()
    private int orientationMode;


    public NewsItem(){

    }

    public String getNewsWhen() {
        return newsWhen;
    }

    public void setNewsWhen(String newsWhen) {
        this.newsWhen = newsWhen;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getOrientationMode() {
        return orientationMode;
    }

    public void setOrientationMode(int orientationMode) {
        this.orientationMode = orientationMode;
    }



    @Override
    public String toString() {
        return "NewsItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", newsWhen='" + newsWhen + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", image=" + image +
                ", orientationMode=" + orientationMode +
                '}';
    }
}
