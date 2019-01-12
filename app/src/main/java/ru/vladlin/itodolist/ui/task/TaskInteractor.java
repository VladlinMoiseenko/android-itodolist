package ru.vladlin.itodolist.ui.task;

import android.os.Handler;
import android.text.TextUtils;

public class TaskInteractor {

    interface OnTaskFinishedListener {

        void onTitledError();

        void onSuccess(String task_title, String task_content);
    }

    public void save(final String task_title, final String task_content, final TaskInteractor.OnTaskFinishedListener listener) {
        new Handler().postDelayed(() -> {
            if (TextUtils.isEmpty(task_title)) {
                listener.onTitledError();
                return;
            }
            listener.onSuccess(task_title, task_content);
        }, 50);
    }
}
