package f.juette.mad.model;

import java.util.List;

public interface IDataItemCRUDOperations {
    public DataItem createDataItem(DataItem item);

    public List<DataItem> readAllDataItems();
}
