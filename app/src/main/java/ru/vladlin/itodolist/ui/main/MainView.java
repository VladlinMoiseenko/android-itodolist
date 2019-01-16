package ru.vladlin.itodolist.ui.main;

import ru.vladlin.itodolist.models.TasksModel;

public interface MainView {

    void showProgress();
    void hideProgress();
    void showToast(String key);
    void displayTasks(TasksModel tasksResponse);
    String getAccessToken();

}