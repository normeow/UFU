
package wildbakery.ufu.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsItem extends Item implements Serializable {

    @SerializedName("news_when")
    @Expose
    private String newsWhen;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("orientation_mode")
    @Expose
    private int orientationMode;

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
