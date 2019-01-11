package ru.vladlin.itodolist.ui.login;

public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToMain();

    void navigateToRegistration();

    void saveAccessToken(String accessToken);

    void showMessage(String message);
}
