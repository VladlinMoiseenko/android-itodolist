package ru.vladlin.itodolist.ui.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import ru.vladlin.itodolist.R;
import ru.vladlin.itodolist.ui.main.MainActivity;


public class TaskActivity extends AppCompatActivity  implements TaskView{

    private ProgressBar progressBar;
    private EditText task_title;
    private EditText task_content;
    private TaskPresenter presenter;

    public static final String APP_PREFERENCES = "mainSettings";
    public static final String APP_PREFERENCES_ACCESS_TOKEN = "AccessToken";
    private SharedPreferences mSettings;
    private String mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        progressBar = findViewById(R.id.progress);
        task_title = findViewById(R.id.task_title);
        task_content = findViewById(R.id.task_content);

        findViewById(R.id.btn_save).setOnClickListener(v -> validateTask());

        presenter = new TaskPresenter(this, new TaskInteractor());
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setTitleError() {
        task_title.setError(getString(R.string.title_error));
    }

    private void validateTask() {
        presenter.validateTask(task_title.getText().toString(), task_content.getText().toString());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public String getAccessToken() {
        mAccessToken = mSettings.getString(APP_PREFERENCES_ACCESS_TOKEN, "");
        return mAccessToken;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

}
