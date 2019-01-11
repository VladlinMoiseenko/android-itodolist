package ru.vladlin.itodolist.net;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.vladlin.itodolist.models.AccesstokenModel;
import ru.vladlin.itodolist.models.AuthorizeModel;
import ru.vladlin.itodolist.models.LogoutModel;
import ru.vladlin.itodolist.models.TasksModel;
import ru.vladlin.itodolist.models.Token;
import ru.vladlin.itodolist.models.Credentials;


public interface NetInterface {

    @POST("v1/authorize")
    Observable<AuthorizeModel> authorize(@Body Credentials credentials);

    @POST("v1/accesstoken")
    Observable<AccesstokenModel> accesstoken(@Body Token token);

    @GET("v1/logout")
    Observable<LogoutModel> logout(@Header("Authorization") String accesstoken);

    @GET("v1/task")
    Observable<TasksModel> getTasks(@Header("Authorization") String accesstoken);


}


