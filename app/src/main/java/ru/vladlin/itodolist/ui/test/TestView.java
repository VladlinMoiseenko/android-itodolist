package ru.vladlin.itodolist.ui.test;

import ru.vladlin.itodolist.models.ApiModel;

public interface TestView {

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void displayMovies(ApiModel movieResponse);

}