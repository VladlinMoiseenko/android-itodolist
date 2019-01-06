package ru.vladlin.itodolist.net;


import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.vladlin.itodolist.models.HubModel;

public interface NetInterface {

    @GET("users/VladlinMoiseenko")
    Observable<HubModel> getMock();


}
