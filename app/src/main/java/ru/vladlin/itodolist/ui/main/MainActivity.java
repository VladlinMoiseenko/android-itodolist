package ru.vladlin.itodolist.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import ru.vladlin.itodolist.R;
import ru.vladlin.itodolist.adapters.TasksAdapter;
import ru.vladlin.itodolist.models.TaskModel;
import ru.vladlin.itodolist.models.TasksModel;
import ru.vladlin.itodolist.ui.login.LoginActivity;
import ru.vladlin.itodolist.ui.task.TaskActivity;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    private ProgressBar progressBar;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        progressBar = findViewById(R.id.progress);
        presenter = new MainPresenter(this);

        findViewById(R.id.fab).setOnClickListener(v -> addTask());

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:

                presenter.logout();

                SharedPreferences mSettings = getSharedPreferences("mainSettings", Context.MODE_PRIVATE);
                mSettings.edit().clear().apply();

                startActivity(new Intent(this, LoginActivity.class));
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayTasks(TasksModel tasksResponse) {
        if(tasksResponse!=null) {
            adapter = new TasksAdapter(tasksResponse.getData(), ru.vladlin.itodolist.ui.main.MainActivity.this, this::onItemClicked);
            recyclerView.setAdapter(adapter);
        }else{
            showMessage("Data response null");
        }
    }

    void onItemClicked(View v, TaskModel itemTask) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.inflate(R.menu.popupmenu);
        popup.setOnMenuItemClickListener((item) -> {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    updateTask(itemTask);
                    return true;
                case R.id.action_delete:
                    deleteTask(itemTask.getId());
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

    void deleteTask(String taskId) {
        presenter.deleteTask(taskId);
    }

    void addTask() {
        startActivity(new Intent(this, TaskActivity.class));
        finish();
    }

    void updateTask(TaskModel itemTask){
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra("taskId", itemTask.getId());
        intent.putExtra("taskTitle", itemTask.getTitle());
        intent.putExtra("taskContent", itemTask.getContent());
        startActivity(intent);
        finish();
    }

    @Override
    public String getAccessToken() {
        SharedPreferences mSettings = getSharedPreferences("mainSettings", Context.MODE_PRIVATE);
        return mSettings.getString("AccessToken", "");
    }

}

