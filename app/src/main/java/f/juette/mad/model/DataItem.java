package f.juette.mad.model;

import android.provider.ContactsContract;

/**
 * Created by sievers on 30.05.16.
 */
public class DataItem {
    private String name;
    private long delay;

    public DataItem(String name, long delay) {
        this.name = name;
        this.delay = delay;
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
                "name: '" + name + '\'' +
                ", delay: " + delay +
                "}";
    }
}
