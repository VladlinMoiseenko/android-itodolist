package ru.vladlin.itodolist.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import ru.vladlin.itodolist.adapters.ApiAdapter;
import ru.vladlin.itodolist.models.ApiModel;
import ru.vladlin.itodolist.ui.test.TestPresenter;

public class TestActivity extends AppCompatActivity implements TestView {
    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;
    private String TAG = "FOFO";
    RecyclerView.Adapter adapter;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TestPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.bind(this);

        progressBar = findViewById(R.id.progress);
        presenter = new TestPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();

        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
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
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayMovies(ApiModel movieResponse) {
        if(movieResponse!=null) {
            Log.d(TAG,movieResponse.getStatus().toString());
            adapter = new ApiAdapter(movieResponse.getData(), ru.vladlin.itodolist.ui.test.TestActivity.this);
            rvMovies.setAdapter(adapter);
        }else{
            Log.d(TAG,"Movies response null");
        }
    }

}
