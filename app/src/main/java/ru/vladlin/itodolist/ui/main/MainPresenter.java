package ru.vladlin.itodolist.ui.main;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.vladlin.itodolist.net.NetClient;
import ru.vladlin.itodolist.net.NetInterface;

import ru.vladlin.itodolist.models.TasksModel;

class MainPresenter {

    private String TAG = "MainPresenter";

    private MainView mainView;

    MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

        getObservable().subscribeWith(getObserver());
    }

    public Observable<TasksModel> getObservable(){
        return NetClient.getRetrofit().create(NetInterface.class)
                .getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<TasksModel> getObserver(){
        return new DisposableObserver<TasksModel>() {

            @Override
            public void onNext(@NonNull TasksModel movieResponse) {
                //Log.d(TAG,"OnNext"+movieResponse.getTotalResults());
                mainView.displayTasks(movieResponse);
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
    }

    public MainView getMainView() {
        return mainView;
    }
}
