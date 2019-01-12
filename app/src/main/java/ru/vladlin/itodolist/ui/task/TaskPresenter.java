package ru.vladlin.itodolist.ui.task;

public class TaskPresenter implements TaskInteractor.OnTaskFinishedListener {

    private TaskView taskView;
    private TaskInteractor taskInteractor;

    TaskPresenter(TaskView taskView, TaskInteractor taskInteractor) {
        this.taskView = taskView;
        this.taskInteractor = taskInteractor;
    }


    public void validateTask(String task_title) {
        if (taskView != null) {
            taskView.showProgress();
        }

        taskInteractor.save(task_title, this);

    }

    @Override
    public void onTitledError() {
        if (taskView != null) {
            taskView.setTitleError();
            taskView.hideProgress();
        }
    }

    @Override
    public void onSuccess(String task_title) {
        if (taskView != null) {
            taskView.showMessage("task_title "+task_title);
        }
    }

    public void onDestroy() {
        taskView = null;
    }

}
