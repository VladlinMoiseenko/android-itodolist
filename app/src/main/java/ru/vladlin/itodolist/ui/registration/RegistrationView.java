package ru.vladlin.itodolist.ui.registration;

public interface RegistrationView {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToMain();

    void saveAccessToken(String accessToken);

    void showMessage(String message);

}
