package ru.vladlin.itodolist.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private String TAG = "MainActivity";

    public static final String APP_PREFERENCES = "mainSettings";
    public static final String APP_PREFERENCES_ACCESS_TOKEN = "AccessToken";
    private SharedPreferences mSettings;
    private String mAccessToken;

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
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
            taskAdd();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_ACCESS_TOKEN)) {
            mAccessToken = mSettings.getString(APP_PREFERENCES_ACCESS_TOKEN, "");
            presenter.onResume(mAccessToken);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
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

                if (mSettings.contains(APP_PREFERENCES_ACCESS_TOKEN)) {
                    mAccessToken = mSettings.getString(APP_PREFERENCES_ACCESS_TOKEN, "");
                    presenter.logout(mAccessToken);
                    mSettings.edit().clear().apply();
                }

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
            Log.d(TAG,"Data response null");
        }
    }

    void onItemClicked(View v, TaskModel itemTask) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.inflate(R.menu.popupmenu);
        popup.setOnMenuItemClickListener((item) -> {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    //Log.d(TAG,"edit" + itemTask.getTitle());
                    showMessage(String.format("%s edited", itemTask.getId()));
                    return true;
                case R.id.action_delete:
                    taskDelete(itemTask.getId());
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

    void taskDelete(String taskId) {
        if (mSettings.contains(APP_PREFERENCES_ACCESS_TOKEN)) {
            showProgress();
            mAccessToken = mSettings.getString(APP_PREFERENCES_ACCESS_TOKEN, "");
            presenter.taskDelete(taskId, mAccessToken);
        }
    }

    void taskAdd() {
        startActivity(new Intent(this, TaskActivity.class));
        finish();
    }


}

