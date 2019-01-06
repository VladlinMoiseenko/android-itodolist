package ru.vladlin.itodolist.main;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.vladlin.itodolist.models.HubModel;
import ru.vladlin.itodolist.net.NetClient;
import ru.vladlin.itodolist.net.NetInterface;


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

       // findItemsInteractor.findItems(this::onFinished);
        getObservable().subscribeWith(getObserver());

    }

    public Observable<HubModel> getObservable(){
        return NetClient.getRetrofit().create(NetInterface.class)
                .getMock()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<HubModel> getObserver(){
        return new DisposableObserver<HubModel>() {

            @Override
            public void onNext(@NonNull HubModel movieResponse) {
                //Log.d(TAG,"OnNext"+movieResponse.getTotalResults());
                mainView.displayMovies(movieResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
               // mvi.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                //mvi.hideProgressBar();
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

    public MainView getMainView() {
        return mainView;
    }
}
