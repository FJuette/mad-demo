package f.juette.mad.model;

import java.util.List;

public interface IDataItemCRUDOperations {

    DataItem createDataItem(DataItem item);

    List<DataItem> readAllDataItems();

    boolean deleteDataItem(long id);

    DataItem updateDataItem(DataItem item);
}
