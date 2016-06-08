package f.juette.mad.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataItem implements Serializable {

    private long id;
    private String name;

    @SerializedName("expiry")
    private long delay;

    public DataItem() {

    }

    public DataItem(String name, long delay) {
        this.name = name;
        this.delay = delay;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", delay: " + delay +
                "}";
    }
}
