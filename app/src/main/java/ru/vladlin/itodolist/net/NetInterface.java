package ru.vladlin.itodolist.net;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.vladlin.itodolist.models.AccesstokenModel;
import ru.vladlin.itodolist.models.AuthorizeModel;
import ru.vladlin.itodolist.models.LogoutModel;
import ru.vladlin.itodolist.models.Task;
import ru.vladlin.itodolist.models.TaskModel;
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

    @GET("v1/task/view/{id}")
    Observable<Task> viewTask(@Path("id") String taskId, @Header("Authorization") String accesstoken);

    @DELETE("v1/task/delete/{id}")
    Observable<Task> deleteTask(@Path("id") String taskId, @Header("Authorization") String accesstoken);

    @POST("v1/task/create")
    Observable<Task> createTask(@Body TaskModel taskModel, @Header("Authorization") String accesstoken);

}


