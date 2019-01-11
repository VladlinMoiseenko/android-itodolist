package ru.vladlin.itodolist.ui.main;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.vladlin.itodolist.models.LogoutModel;
import ru.vladlin.itodolist.net.NetClient;
import ru.vladlin.itodolist.net.NetInterface;

import ru.vladlin.itodolist.models.TasksModel;

class MainPresenter {

    private String TAG = "MainPresenter";

    private MainView mainView;

    MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    void onResume(String accesstoken) {
        if (mainView != null) {
            mainView.showProgress();
        }

        getObservable(accesstoken).subscribeWith(getObserver());
    }

    public Observable<TasksModel> getObservable(String accesstoken){
        return NetClient.getRetrofit().create(NetInterface.class)
                .getTasks(accesstoken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<TasksModel> getObserver(){
        return new DisposableObserver<TasksModel>() {

            @Override
            public void onNext(@NonNull TasksModel tasksResponse) {
                mainView.displayTasks(tasksResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                mainView.showMessage("Error retrieving data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                mainView.hideProgress();
            }
        };
    }

    void logout(String accesstoken) {
        if (mainView != null) {
            mainView.showProgress();
        }

        getObservableLogout(accesstoken).subscribeWith(getObserverLogout());
    }

    public Observable<LogoutModel> getObservableLogout(String accesstoken){
        return NetClient.getRetrofit().create(NetInterface.class)
                .logout(accesstoken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<LogoutModel> getObserverLogout(){
        return new DisposableObserver<LogoutModel>() {

            @Override
            public void onNext(@NonNull LogoutModel logoutResponse) {
                //Log.d(TAG,"logoutResponse:"+logoutResponse.getStatus());
                //mainView.displayTasks(tasksResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                mainView.showMessage("Error retrieving data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                mainView.hideProgress();
            }
        };
    }

    void onDestroy() {
        mainView = null;
        getObserver().dispose();
        getObserverLogout().dispose();
    }

//    public MainView getMainView() {
//        return mainView;
//    }
}
