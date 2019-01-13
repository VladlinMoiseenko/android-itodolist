package ru.vladlin.itodolist.ui.registration;

public class RegistrationPresenter implements RegistrationInteractor.OnLoginFinishedListener{

    private String TAG = "RegistrationPresenter";
    private RegistrationView registrationView;
    private RegistrationInteractor registrationInteractor;

    RegistrationPresenter(RegistrationView registrationView, RegistrationInteractor registrationInteractor) {
        this.registrationView = registrationView;
        this.registrationInteractor = registrationInteractor;
    }

    public void validateCredentials(String username, String password, String email) {
        if (registrationView != null) {
            registrationView.showProgress();
        }
        registrationInteractor.login(username, password, this);
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
    public void onSuccess(String username, String password) {
        if (registrationView != null) {
            //getObservable(username, password).subscribeWith(getObserver());
        }
    }

    public void onDestroy() {
        registrationView = null;
//        getObserverToken().dispose();

    }
}
