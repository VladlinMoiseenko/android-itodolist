package ru.vladlin.itodolist.ui.registration;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.vladlin.itodolist.models.AccesstokenModel;
import ru.vladlin.itodolist.models.AuthorizeModel;
import ru.vladlin.itodolist.models.Credentials;
import ru.vladlin.itodolist.models.ProfileModel;
import ru.vladlin.itodolist.models.Token;
import ru.vladlin.itodolist.net.NetClient;
import ru.vladlin.itodolist.net.NetInterface;

public class RegistrationPresenter implements RegistrationInteractor.OnLoginFinishedListener{

    private RegistrationView registrationView;
    private RegistrationInteractor registrationInteractor;
    private Credentials credentials;

    RegistrationPresenter(RegistrationView registrationView, RegistrationInteractor registrationInteractor) {
        this.registrationView = registrationView;
        this.registrationInteractor = registrationInteractor;
    }

    public void validateCredentials(String username, String password, String email) {
        if (registrationView != null) {
            registrationView.showProgress();
        }
        credentials = new Credentials(username, password, email);
        registrationInteractor.login(username, password, email, this);
    }

    @Override
    public void onUsernameError() {
        if (registrationView != null) {
            registrationView.setUsernameError();
            registrationView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (registrationView != null) {
            registrationView.setPasswordError();
            registrationView.hideProgress();
        }
    }

    @Override
    public void onEmailError() {
        if (registrationView != null) {
            registrationView.setEmailError();
            registrationView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (registrationView != null) {
            registration();
        }
    }

    private void registration() {
        getObservableRegistration().subscribeWith(getObserverRegistration());
    }

    public Observable<ProfileModel> getObservableRegistration(){
        return NetClient.getRetrofit().create(NetInterface.class)
                .register(credentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<ProfileModel> getObserverRegistration(){
        return new DisposableObserver<ProfileModel>() {
            @Override
            public void onNext(@NonNull ProfileModel response) {
            }
            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                registrationView.hideProgress();
                registrationView.showMessage("Error retrieving data");
            }
            @Override
            public void onComplete() {
                registrationView.hideProgress();
                authorize();
            }
        };
    }

    private void authorize() {
        getObservableAuthorize().subscribeWith(getObserverAuthorize());
    }


    public Observable<AuthorizeModel> getObservableAuthorize(){
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
                e.printStackTrace();
                registrationView.hideProgress();
                registrationView.showMessage("Error retrieving data");
            }
            @Override
            public void onComplete() {
                registrationView.hideProgress();
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
                registrationView.saveAccessToken(accessToken);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }
            @Override
            public void onComplete() {
                registrationView.hideProgress();
                registrationView.navigateToMain();
            }
        };
    }

    public void onDestroy() {
        registrationView = null;
        getObserverRegistration().dispose();
        getObserverAuthorize().dispose();
        getObserverToken().dispose();
    }
}
