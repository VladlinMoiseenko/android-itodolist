package ru.vladlin.itodolist.ui.login;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import ru.vladlin.itodolist.models.AuthorizeModel;
import ru.vladlin.itodolist.models.User;
import ru.vladlin.itodolist.net.NetClient;
import ru.vladlin.itodolist.net.NetInterface;

public class LoginPresenter {

    private String TAG = "FOLogin";
    private LoginView loginView;

    LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }


    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        getObservable().subscribeWith(getObserver());

    }

    public Observable<AuthorizeModel> getObservable(){

        User user = new User("demo", "123456");

        return NetClient.getRetrofit().create(NetInterface.class)
                .authorize(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<AuthorizeModel> getObserver(){
        return new DisposableObserver<AuthorizeModel>() {

            @Override
            public void onNext(@NonNull AuthorizeModel response) {
                Log.d(TAG,"response:"+response.getData().getAuthorizationCode());
                loginView.saveAuthorizationCode(response.getData().getAuthorizationCode());

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                //loginView.showMessage("Error retrieving data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                loginView.hideProgress();
                loginView.navigateToHome();
            }
        };
    }


    public void onDestroy() {
        loginView = null;
        getObserver().dispose();
    }

//    @Override
//    public void onUsernameError() {
//        if (loginView != null) {
//            loginView.setUsernameError();
//            loginView.hideProgress();
//        }
//    }
//
//    @Override
//    public void onPasswordError() {
//        if (loginView != null) {
//            loginView.setPasswordError();
//            loginView.hideProgress();
//        }
//    }

//    @Override
//    public void onSuccess() {
//        if (loginView != null) {
//            loginView.navigateToHome();
//        }
//    }
}
