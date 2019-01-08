package ru.vladlin.itodolist.net;


import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.vladlin.itodolist.models.TasksModel;

public interface NetInterface {

    @GET("v1/task")
    Observable<TasksModel> getTasks();

}
