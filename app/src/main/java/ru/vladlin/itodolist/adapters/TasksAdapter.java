package ru.vladlin.itodolist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.vladlin.itodolist.R;

import ru.vladlin.itodolist.models.TaskModel;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ApiHolder> {

    List<TaskModel> movieList;

    Context context;

    public TasksAdapter(List<TaskModel> movieList, Context context) {
        this.movieList = movieList;
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

        holder.tTitle.setText(movieList.get(position).getTitle());
        holder.tContent.setText(movieList.get(position).getContent());
        holder.tCreatedAt.setText(movieList.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ApiHolder extends RecyclerView.ViewHolder {

        TextView tTitle, tContent, tCreatedAt;

        public ApiHolder(View v) {
            super(v);
            tTitle = (TextView) v.findViewById(R.id.tTitle);
            tContent = (TextView) v.findViewById(R.id.tContent);
            tCreatedAt = (TextView) v.findViewById(R.id.tCreatedAt);

        }
    }
}
