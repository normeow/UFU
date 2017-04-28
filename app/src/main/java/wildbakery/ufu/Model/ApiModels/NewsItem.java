
package wildbakery.ufu.Model.ApiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import wildbakery.ufu.Constants;

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
    @DatabaseField()
    private Category category;

    @SerializedName("image")
    @Expose
    private Image image;

    @SerializedName("orientation_mode")
    @Expose
    @DatabaseField()
    private int orientationMode;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    private String imagePath;

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
        return "SaleItem{" +
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
