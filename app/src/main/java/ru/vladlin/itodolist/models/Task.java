package ru.vladlin.itodolist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {
    private String username;
    private String password;

    public Task(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
