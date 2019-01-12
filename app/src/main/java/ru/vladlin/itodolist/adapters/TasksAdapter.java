package ru.vladlin.itodolist.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.vladlin.itodolist.R;

import ru.vladlin.itodolist.models.TaskModel;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ApiHolder> {

    private String TAG = "TasksAdapter";

    List<TaskModel> tasksList;

    Context context;

    CardView cv;

    public TasksAdapter(List<TaskModel> tasksList, Context context) {
        this.tasksList = tasksList;
        this.context = context;
    }

    @Override
    public ApiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_main_item,parent,false);
        ApiHolder mh = new ApiHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ApiHolder holder, int position) {

        holder.tTitle.setText(tasksList.get(position).getTitle());
        holder.tContent.setText(tasksList.get(position).getContent());
        holder.tCreatedAt.setText(tasksList.get(position).getCreatedAt());

        holder.cv.setOnClickListener((view) -> {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                context = view.getContext();
                popup.inflate(R.menu.popupmenu);
                popup.setOnMenuItemClickListener((item) -> {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                Log.d(TAG,"edit" + tasksList.get(position).getTitle());
                                return true;
                            case R.id.action_delete:
                                Log.d(TAG,"delete" + tasksList.get(position).getId());
                                return true;
                            default:
                                return false;
                        }
                });
                popup.show();
        });

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ApiHolder extends RecyclerView.ViewHolder {

        CardView cv;

        TextView tTitle, tContent, tCreatedAt;

        public ApiHolder(View v) {
            super(v);

            cv = (CardView)itemView.findViewById(R.id.cv);

            tTitle = (TextView) v.findViewById(R.id.tTitle);
            tContent = (TextView) v.findViewById(R.id.tContent);
            tCreatedAt = (TextView) v.findViewById(R.id.tCreatedAt);

        }
    }
}
