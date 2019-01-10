package ru.vladlin.itodolist.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import ru.vladlin.itodolist.R;
import ru.vladlin.itodolist.ui.main.MainActivity;
import ru.vladlin.itodolist.ui.registration.RegistrationActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        findViewById(R.id.button).setOnClickListener(v -> validateCredentials());
        findViewById(R.id.btn_registration).setOnClickListener(v -> navigateToRegistration());

        presenter = new LoginPresenter(this);
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
    public void navigateToHome() {
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
}
