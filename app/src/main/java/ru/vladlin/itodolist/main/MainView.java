package ru.vladlin.itodolist.main;

import ru.vladlin.itodolist.models.HubModel;

public interface MainView {

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void displayMovies(HubModel movieResponse);
}