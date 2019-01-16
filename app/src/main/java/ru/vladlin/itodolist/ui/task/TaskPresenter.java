package ru.vladlin.itodolist.ui.task;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.vladlin.itodolist.models.Task;
import ru.vladlin.itodolist.models.TaskModel;
import ru.vladlin.itodolist.net.NetClient;
import ru.vladlin.itodolist.net.NetInterface;

public class TaskPresenter implements TaskInteractor.OnTaskFinishedListener {

    private TaskView taskView;
    private TaskInteractor taskInteractor;

    TaskPresenter(TaskView taskView, TaskInteractor taskInteractor) {
        this.taskView = taskView;
        this.taskInteractor = taskInteractor;
    }

    public void validateTask(String task_title, String task_content) {
        if (taskView != null) {
            taskView.showProgress();
        }
        taskInteractor.save(task_title, task_content, this);
    }

    @Override
    public void onTitledError() {
        if (taskView != null) {
            taskView.setTitleError();
            taskView.hideProgress();
        }
    }

    @Override
    public void onSuccess(String task_title, String task_content) {
        if (taskView != null) {

            if (taskView.getIdTask() == null) {
                getObservableSave(task_title, task_content).subscribeWith(getObserverSave());
            } else {
                getObservableUpdate(taskView.getIdTask(), task_title, task_content).subscribeWith(getObserverSave());
            }
        }
    }

    public Observable<Task> getObservableUpdate(String taskId, String title, String content){
        TaskModel taskModel = new TaskModel(title, content);
        String accessToken = taskView.getAccessToken();
        return NetClient.getRetrofit().create(NetInterface.class)
                .updateTask(taskId, taskModel, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Task> getObservableSave(String title, String content){
        TaskModel taskModel = new TaskModel(title, content);
        String mAccessToken = taskView.getAccessToken();
        return NetClient.getRetrofit().create(NetInterface.class)
                .createTask(taskModel, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<Task> getObserverSave(){
        return new DisposableObserver<Task>() {
            @Override
            public void onNext(@NonNull Task response) {
            }
            @Override
            public void onError(@NonNull Throwable e) {
                //e.printStackTrace();
                taskView.hideProgress();
                taskView.showToast("error_retrieving_data");
            }
            @Override
            public void onComplete() {
                taskView.navigateToMain();
            }
        };
    }

    public void onDestroy() {
        taskView = null;
        getObserverSave().dispose();
    }

}
