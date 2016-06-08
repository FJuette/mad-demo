package f.juette.mad.model;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface IWebApplicationRESTApiWithRetrofit {

    @Headers("Content-Type: application/json")
    @POST("/todos")
    public DataItem createItem(@Body DataItem item);

    @GET("/todos")
    public List<DataItem> reallAllItems();

    @GET("/todos/{id}")
    public DataItem readItem(@Path("id") long dataItemId);

    @PUT("/todos/{id}")
    public DataItem updateItem(@Path("id") long dataItemId, @Body DataItem item);

    @Headers("Content-Type: application/json")
    @DELETE("/todos/{id}")
    public boolean deleteItem(@Path("id") long dataItemId);
}
