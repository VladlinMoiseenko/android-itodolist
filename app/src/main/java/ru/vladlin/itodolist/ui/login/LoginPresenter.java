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
import ru.vladlin.itodolist.models.Credentials;
import ru.vladlin.itodolist.net.NetClient;
import ru.vladlin.itodolist.net.NetInterface;

public class LoginPresenter implements LoginInteractor.OnLoginFinishedListener {

    private String TAG = "LoginPresenter";
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    LoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }


    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        loginInteractor.login(username, password, this);

    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess(String username, String password) {
        if (loginView != null) {
            //loginView.navigateToHome();
            getObservable(username, password).subscribeWith(getObserver());
        }
    }


    public Observable<AuthorizeModel> getObservable(String username, String password){

        Credentials credentials = new Credentials(username, password);

        return NetClient.getRetrofit().create(NetInterface.class)
                .authorize(credentials)
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
                loginView.hideProgress();
                loginView.showMessage("Error retrieving data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                loginView.hideProgress();
            }
        };
    }

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

}
