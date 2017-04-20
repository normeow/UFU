package wildbakery.ufu.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tatiana on 10/04/2017.
 */

public class Item {
    @SerializedName("id")
    @Expose
    protected int id;
    @SerializedName("name")
    @Expose
    protected String name;
    @SerializedName("description")
    @Expose
    protected String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
