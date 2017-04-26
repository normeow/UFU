
package wildbakery.ufu.Model.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventItem extends Item{

    @SerializedName("event_when")
    @Expose
    private String eventWhen;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("orientation_mode")
    @Expose
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
