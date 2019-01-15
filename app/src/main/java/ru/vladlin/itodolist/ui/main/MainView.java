package ru.vladlin.itodolist.ui.main;

import ru.vladlin.itodolist.models.TasksModel;

public interface MainView {

    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void displayTasks(TasksModel tasksResponse);
    String getAccessToken();

}