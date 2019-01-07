package ru.vladlin.itodolist.ui.test;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.vladlin.itodolist.net.NetClient;
import ru.vladlin.itodolist.net.NetInterface;

import ru.vladlin.itodolist.ui.test.TestView;


import ru.vladlin.itodolist.models.ApiModel;

class TestPresenter {

    private String TAG = "MainPresenter";

    private TestView mainView;

    TestPresenter(TestView mainView) {
        this.mainView = mainView;
    }

    void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

        getObservable().subscribeWith(getObserver());

    }

    public Observable<ApiModel> getObservable(){
        return NetClient.getRetrofit().create(NetInterface.class)
                .getMock()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<ApiModel> getObserver(){
        return new DisposableObserver<ApiModel>() {

            @Override
            public void onNext(@NonNull ApiModel movieResponse) {
                //Log.d(TAG,"OnNext"+movieResponse.getTotalResults());
                mainView.displayMovies(movieResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                //mainView.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                mainView.hideProgress();
            }
        };
    }




    void onItemClicked(String item) {
        if (mainView != null) {
            mainView.showMessage(String.format("%s clicked", item));
        }
    }

    void onDestroy() {
        mainView = null;
    }

    public void onFinished(List<String> items) {
        if (mainView != null) {
            //mainView.setItems(items);
            mainView.hideProgress();
        }
    }

    public TestView getMainView() {
        return mainView;
    }
}
