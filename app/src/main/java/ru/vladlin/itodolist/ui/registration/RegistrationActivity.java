package ru.vladlin.itodolist.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.vladlin.itodolist.R;
import ru.vladlin.itodolist.ui.main.MainActivity;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private EditText email;
    private RegistrationPresenter presenter;

    public static final String APP_PREFERENCES = "mainSettings";
    public static final String APP_PREFERENCES_ACCESS_TOKEN = "AccessToken";
    private SharedPreferences mSettings;
    private String mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBar = findViewById(R.id.progress);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        findViewById(R.id.btn_register).setOnClickListener(v -> validateCredentials());

        presenter = new RegistrationPresenter(this, new RegistrationInteractor());

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void setEmailError() {
        password.setError(getString(R.string.email_error));
    }

    private void validateCredentials() {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString(), email.getText().toString());
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
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
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveAccessToken(String accessToken) {
        if(accessToken!=null) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_ACCESS_TOKEN, accessToken);
            editor.apply();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

}
