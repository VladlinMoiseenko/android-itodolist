package ru.vladlin.itodolist.net;


import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.vladlin.itodolist.models.HubModel;
import ru.vladlin.itodolist.models.ApiModel;

public interface NetInterface {

//    @GET("users/VladlinMoiseenko")
//    Observable<HubModel> getMock();

    @GET("v1/task")
    Observable<ApiModel> getMock();

}
