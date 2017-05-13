
package wildbakery.ufu.Model.ApiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import wildbakery.ufu.Constants;

@DatabaseTable(tableName = Constants.TABLES.TABLE_EVENT)
public class EventItem extends Item{

    @SerializedName("event_when")
    @Expose
    @DatabaseField
    private String eventWhen;

    @SerializedName("image")
    @Expose
    @DatabaseField(foreign =  true)
    private Image image;

    @SerializedName("orientation_mode")
    @Expose
    @DatabaseField
    private int orientationMode;

    public String getEventWhen() {
        return eventWhen;
    }

    public void setEventWhen(String eventWhen) {
        this.eventWhen = eventWhen;
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
                ", description='" + description + '\'' +
                ", when='" + eventWhen + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}
