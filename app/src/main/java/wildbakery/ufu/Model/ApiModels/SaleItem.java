
package wildbakery.ufu.Model.ApiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import wildbakery.ufu.Constants;

@DatabaseTable(tableName = Constants.TABLES.TABLE_SALES)
public class SaleItem extends Item{

    @SerializedName("who")
    @Expose
    @DatabaseField
    private String who;

    @SerializedName("image")
    @Expose
    @DatabaseField(foreign = true)
    private Image image;

    @SerializedName("orientation_mode")
    @Expose
    @DatabaseField
    private int orientationMode;

    @SerializedName("date_start")
    @Expose
    @DatabaseField
    private String dateStart;

    @SerializedName("date_end")
    @Expose
    @DatabaseField
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
