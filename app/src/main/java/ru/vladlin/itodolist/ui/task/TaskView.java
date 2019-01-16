package ru.vladlin.itodolist.ui.task;

public interface TaskView {

    void showProgress();
    void hideProgress();
    void setTitleError();
    void showToast(String key);
    void navigateToMain();
    String getAccessToken();
    String getIdTask();

}
