
package wildbakery.ufu.Model.ApiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleItem extends Item{

    @SerializedName("who")
    @Expose
    private String who;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("orientation_mode")
    @Expose
    private int orientationMode;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("date_end")
    @Expose
    private String dateEnd;

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
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

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

}
