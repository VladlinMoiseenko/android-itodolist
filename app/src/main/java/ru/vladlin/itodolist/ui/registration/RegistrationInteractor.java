package ru.vladlin.itodolist.ui.registration;

import android.os.Handler;
import android.text.TextUtils;

public class RegistrationInteractor {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess(String username, String password);
    }

    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(() -> {
            if (TextUtils.isEmpty(username)) {
                listener.onUsernameError();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                listener.onPasswordError();
                return;
            }
            listener.onSuccess(username, password);
        }, 500);
    }
}
