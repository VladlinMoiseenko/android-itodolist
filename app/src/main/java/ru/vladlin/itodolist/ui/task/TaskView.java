package ru.vladlin.itodolist.ui.task;

public interface TaskView {

    void showProgress();

    void hideProgress();

    void setTitleError();

    void showMessage(String message);

}
