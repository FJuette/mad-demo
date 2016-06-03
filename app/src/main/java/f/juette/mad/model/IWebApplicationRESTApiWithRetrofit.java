package f.juette.mad.model;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface IWebApplicationRESTApiWithRetrofit {

    @POST("/dataitems")
    public DataItem createItem(@Body DataItem item);

    @GET("/dataitems")
    public List<DataItem> reallAllItems();

    @GET("/dataitems/{id}")
    public DataItem readItem(@Path("id") long dataItemId);

    @PUT("/dataitems")
    public DataItem updateItem(@Body DataItem item);

    @DELETE("/dataitems/{id}")
    public boolean deleteItem(@Path("id") long dataItemId);
}
