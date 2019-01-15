package ru.vladlin.itodolist.ui.login;

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
import ru.vladlin.itodolist.ui.registration.RegistrationActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginPresenter presenter;

    public static final String APP_PREFERENCES = "mainSettings";
    public static final String APP_PREFERENCES_ACCESS_TOKEN = "AccessToken";
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        findViewById(R.id.btn_login).setOnClickListener(v -> validateCredentials());
        findViewById(R.id.btn_registration).setOnClickListener(v -> navigateToRegistration());

        presenter = new LoginPresenter(this, new LoginInteractor());

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSettings.contains(APP_PREFERENCES_ACCESS_TOKEN)) {
            navigateToMain();
        }
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
        presenter.destroy();
        super.onDestroy();
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
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void navigateToRegistration() {
        startActivity(new Intent(this, RegistrationActivity.class));
        finish();
    }

    private void validateCredentials() {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessageKey(String key) {
        int strId = getResources().getIdentifier(key, "string", getPackageName());
        showMessage(getString(strId));
    }

}
