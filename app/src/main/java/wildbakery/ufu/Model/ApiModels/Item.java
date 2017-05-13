package wildbakery.ufu.Model.ApiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

import wildbakery.ufu.Constants;

/**
 * Created by Tatiana on 10/04/2017.
 */

public class Item implements Serializable{

    // for database
    @DatabaseField(generatedId = true, columnName = Constants.TABLES.COLUMN_PK_ID)
    protected int primaryKeyId;

    @SerializedName("id")
    @Expose
    @DatabaseField()
    protected int id;

    @SerializedName("name")
    @Expose
    @DatabaseField
    protected String name;

    @SerializedName("description")
    @Expose
    @DatabaseField
    protected String description;

    public Item(){}

    public int getPrimaryKeyId() {
        return primaryKeyId;
    }

    public void setPrimaryKeyId(int primaryKeyId) {
        this.primaryKeyId = primaryKeyId;
    }

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
