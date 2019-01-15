package ru.vladlin.itodolist.ui.login;

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
            getObservableAuthorize(username, password).subscribeWith(getObserverAuthorize());
        }
    }

    public Observable<AuthorizeModel> getObservableAuthorize(String username, String password){

        Credentials credentials = new Credentials(username, password, null);

        return NetClient.getRetrofit().create(NetInterface.class)
                .authorize(credentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<AuthorizeModel> getObserverAuthorize(){
        return new DisposableObserver<AuthorizeModel>() {

            @Override
            public void onNext(@NonNull AuthorizeModel response) {
                String authorizationCode = response.getData().getAuthorizationCode();
                getToken(authorizationCode);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                //e.printStackTrace();
                loginView.hideProgress();
                loginView.showMessageKey("error_retrieving_data");
            }

            @Override
            public void onComplete() {
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
                loginView.saveAccessToken(accessToken);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                //e.printStackTrace();
            }

            @Override
            public void onComplete() {
                loginView.navigateToMain();
            }
        };
    }

    public void destroy() {
        loginView = null;
        getObserverAuthorize().dispose();
        getObserverToken().dispose();
    }

}
