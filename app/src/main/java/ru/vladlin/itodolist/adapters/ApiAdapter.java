package ru.vladlin.itodolist.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.vladlin.itodolist.R;

import ru.vladlin.itodolist.models.ApiModel;
import ru.vladlin.itodolist.models.DatumModel;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ApiHolder> {

    List<DatumModel> movieList;

    Context context;

    public ApiAdapter(List<DatumModel> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public ApiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_movies,parent,false);
        ApiHolder mh = new ApiHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ApiHolder holder, int position) {

        holder.tvTitle.setText(movieList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ApiHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvOverview,tvReleaseDate;

        public ApiHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);

        }
    }
}
