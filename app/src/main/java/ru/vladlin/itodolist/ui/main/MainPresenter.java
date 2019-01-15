package ru.vladlin.itodolist.ui.main;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.vladlin.itodolist.models.LogoutModel;
import ru.vladlin.itodolist.models.Task;
import ru.vladlin.itodolist.net.NetClient;
import ru.vladlin.itodolist.net.NetInterface;

import ru.vladlin.itodolist.models.TasksModel;

class MainPresenter {

    private MainView mainView;

    MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

        viewTasks();
    }

    void viewTasks(){
        getObservableViewTasks().subscribeWith(getObserverViewTasks());
    }

    public Observable<TasksModel> getObservableViewTasks(){
        String accessToken = mainView.getAccessToken();
        return NetClient.getRetrofit().create(NetInterface.class)
                .getTasks(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<TasksModel> getObserverViewTasks(){
        return new DisposableObserver<TasksModel>() {

            @Override
            public void onNext(@NonNull TasksModel tasksResponse) {
                mainView.displayTasks(tasksResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                //e.printStackTrace();
                mainView.showMessage("Error retrieving data");
            }

            @Override
            public void onComplete() {
                mainView.hideProgress();
            }
        };
    }

    void logout() {
        if (mainView != null) {
            mainView.showProgress();
        }

        getObservableLogout().subscribeWith(getObserverLogout());
    }

    public Observable<LogoutModel> getObservableLogout(){
        String accessToken = mainView.getAccessToken();
        return NetClient.getRetrofit().create(NetInterface.class)
                .logout(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<LogoutModel> getObserverLogout(){
        return new DisposableObserver<LogoutModel>() {

            @Override
            public void onNext(@NonNull LogoutModel logoutResponse) {
                //mainView.displayTasks(tasksResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                //e.printStackTrace();
                mainView.showMessage("Error retrieving data");
            }

            @Override
            public void onComplete() {
                mainView.hideProgress();
            }
        };
    }


    void deleteTask(String taskId) {
        getObservableDelete(taskId).subscribeWith(getObserverDelete());
    }

    public Observable<Task> getObservableDelete(String taskId){
        String accessToken = mainView.getAccessToken();
        return NetClient.getRetrofit().create(NetInterface.class)
                .deleteTask(taskId, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<Task> getObserverDelete(){
        return new DisposableObserver<Task>() {

            @Override
            public void onNext(@NonNull Task taskResponse) {
                mainView.showMessage(String.format("%s Задача удалена", taskResponse.getStatus()));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                //e.printStackTrace();
                mainView.showMessage("Error retrieving data");
            }

            @Override
            public void onComplete() {
                viewTasks();
            }
        };
    }

    void onDestroy() {
        mainView = null;
        getObserverViewTasks().dispose();
        getObserverLogout().dispose();
        getObserverDelete().dispose();
    }

}
