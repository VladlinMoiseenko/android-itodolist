package ru.vladlin.itodolist.net;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.vladlin.itodolist.models.AccesstokenModel;
import ru.vladlin.itodolist.models.AuthorizeModel;
import ru.vladlin.itodolist.models.Token;
import ru.vladlin.itodolist.models.User;
import ru.vladlin.itodolist.models.TasksModel;

public interface NetInterface {

    @GET("v1/task")
    Observable<TasksModel> getTasks();

    @POST("v1/authorize")
    Observable<AuthorizeModel> authorize(@Body User user);

    @POST("v1/accesstoken")
    Observable<AccesstokenModel> accesstoken(@Body Token token);

}


