package ru.vladlin.itodolist.ui.task;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import ru.vladlin.itodolist.R;


public class TaskActivity extends AppCompatActivity  implements TaskView{

    private ProgressBar progressBar;
    private EditText task_title;
    private TaskPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        progressBar = findViewById(R.id.progress);
        task_title = findViewById(R.id.task_title);

        findViewById(R.id.btn_save).setOnClickListener(v -> validateTask());

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
        presenter.validateTask(task_title.getText().toString());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

}
