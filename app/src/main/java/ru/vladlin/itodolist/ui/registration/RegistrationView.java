package ru.vladlin.itodolist.ui.registration;

public interface RegistrationView {

    void showProgress();
    void hideProgress();
    void setUsernameError();
    void setPasswordError();
    void setEmailError();
    void navigateToMain();
    void saveAccessToken(String accessToken);
    void showToast(String key);

}
