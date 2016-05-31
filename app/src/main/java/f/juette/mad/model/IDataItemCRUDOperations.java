package f.juette.mad.model;

import java.util.List;

import f.juette.mad.model.impl.DataItem;

public interface IDataItemCRUDOperations {
    public DataItem createDataItem(DataItem item);

    public List<DataItem> readAllDataItems();
}
