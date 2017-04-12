
package wildbakery.ufu.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JobItem extends Item implements Serializable {

    @SerializedName("wage")
    @Expose
    private int wage;
    @SerializedName("count")
    @Expose
    private String count;


    public String getCount(){
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The wage
     */
    public int getWage() {
        return wage;
    }

    /**
     * 
     * @param wage
     *     The wage
     */
    public void setWage(int wage) {
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