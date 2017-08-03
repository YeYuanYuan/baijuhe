package com.lin.app.request.retrofit;

import com.google.gson.JsonObject;
import com.lin.app.data.respone.WeatherRespone;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by lpds on 2017/5/31.
 */
public interface WeatherApi {

    String KEY = "1e47c1e7361fe";
    String PATH = "http://apicloud.mob.com/v1/weather/";

    @GET("/query?")
    Observable<JsonObject> getByCity(@Query("key") String key,@Query("city") String city,@Query("province") String province);


    @GET("citys?")
    Observable<WeatherRespone> getAllMsg(@Query("key") String key);

}
