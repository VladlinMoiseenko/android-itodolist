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
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        task_title = findViewById(R.id.task_title);
        task_content = findViewById(R.id.task_content);

        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");

        task_title.setText(intent.getStringExtra("taskTitle"));
        task_content.setText(intent.getStringExtra("taskContent"));

        findViewById(R.id.btn_save).setOnClickListener(v -> validateTask());
        progressBar = findViewById(R.id.progress);
        presenter = new TaskPresenter(this, new TaskInteractor());
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
    public void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public String getAccessToken() {
        SharedPreferences mSettings = getSharedPreferences("mainSettings", Context.MODE_PRIVATE);
        return mSettings.getString("AccessToken", "");
    }

    @Override
    public String getIdTask() {
        return taskId;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showToast(String key) {
        int strId = getResources().getIdentifier(key, "string", getPackageName());
        Toast.makeText(this, getString(strId), Toast.LENGTH_LONG).show();
    }

}
