package ru.vladlin.itodolist.ui.login;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import ru.vladlin.itodolist.models.AccesstokenModel;
import ru.vladlin.itodolist.models.AuthorizeModel;
import ru.vladlin.itodolist.models.Token;
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
                String authorizationCode = response.getData().getAuthorizationCode();
                //Log.d(TAG,"response:"+authorizationCode);
                getToken(authorizationCode);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                loginView.hideProgress();
            }
        };
    }


    //////////////////////

    private void getToken(String authorizationCode) {

        getObservableToken(authorizationCode).subscribeWith(getObserverToken());

    }

    public Observable<AccesstokenModel> getObservableToken(String authorizationCode){

        Token token = new Token(authorizationCode);

        return NetClient.getRetrofit().create(NetInterface.class)
                .accesstoken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<AccesstokenModel> getObserverToken(){
        return new DisposableObserver<AccesstokenModel>() {

            @Override
            public void onNext(@NonNull AccesstokenModel response) {
                String accessToken = response.getData().getAccessToken();
                Log.d(TAG,"accessToken:"+accessToken);
                loginView.saveAccessToken(accessToken);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                loginView.hideProgress();
                loginView.navigateToMain();
            }
        };
    }

    public void onDestroy() {
        loginView = null;
        getObserver().dispose();
        getObserverToken().dispose();
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
//            loginView.navigateToMain();
//        }
//    }
}
