
package wildbakery.ufu.Model.ApiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import wildbakery.ufu.Constants;

@DatabaseTable(tableName = Constants.TABLES.TABLE_JOBS)
public class JobItem extends Item{

    @SerializedName("wage")
    @Expose
    @DatabaseField
    private String wage;
    @SerializedName("count")
    @Expose
    @DatabaseField
    private String count;
    @SerializedName("short_description")
    @Expose
    @DatabaseField
    private String shortDescription;


    public String getCount(){
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * 
     * @return
     *     The wage
     */
    public String getWage() {
        return wage;
    }

    /**
     * 
     * @param wage
     *     The wage
     */
    public void setWage(String wage) {
        this.wage = wage;
    }


    @Override
    public String toString() {
        return "SaleItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}