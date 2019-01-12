package ru.vladlin.itodolist.ui.task;

import android.os.Handler;
import android.text.TextUtils;

public class TaskInteractor {

    interface OnTaskFinishedListener {

        void onTitledError();

        void onSuccess(String task_title);
    }

    public void save(final String task_title, final TaskInteractor.OnTaskFinishedListener listener) {
        new Handler().postDelayed(() -> {
            if (TextUtils.isEmpty(task_title)) {
                listener.onTitledError();
                return;
            }
            listener.onSuccess(task_title);
        }, 50);
    }
}
