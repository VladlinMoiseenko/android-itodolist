package ru.vladlin.itodolist.ui.registration;

import android.os.Handler;
import android.text.TextUtils;

public class RegistrationInteractor {

    interface OnLoginFinishedListener {
        void onUsernameError();
        void onPasswordError();
        void onEmailError();
        void onSuccess();
    }

    public void login(final String username, final String password, final String email, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(() -> {
            if (TextUtils.isEmpty(username)) {
                listener.onUsernameError();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                listener.onPasswordError();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                listener.onEmailError();
                return;
            }
            listener.onSuccess();
        }, 500);
    }
}
