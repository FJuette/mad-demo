package f.juette.mad.model.impl;

import java.util.List;

import f.juette.mad.model.DataItem;
import f.juette.mad.model.IDataItemCRUDOperations;
import f.juette.mad.model.IWebApplicationRESTApiWithRetrofit;
import retrofit.RestAdapter;

public class RemoteDataItemCRUDOperationsImpl implements IDataItemCRUDOperations {

    private IWebApplicationRESTApiWithRetrofit restapi;

    public RemoteDataItemCRUDOperationsImpl() {
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint("http://10.0.3.2:8080/api")
                .build();
        restapi = retrofit.create(IWebApplicationRESTApiWithRetrofit.class);
    }

    @Override
    public DataItem createDataItem(DataItem item) {
        return restapi.createItem(item);
    }

    @Override
    public List<DataItem> readAllDataItems() {
        return restapi.reallAllItems();
    }

    @Override
    public boolean deleteDataItem(long id) {
        return restapi.deleteItem(id);
    }
}
