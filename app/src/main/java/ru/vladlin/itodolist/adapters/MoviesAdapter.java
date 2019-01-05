package ru.vladlin.itodolist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.vladlin.itodolist.R;
import ru.vladlin.itodolist.models.HubModel;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

    HubModel movieList;

    Context context;

    public MoviesAdapter(HubModel movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.second_fragment,parent,false);
        MoviesHolder mh = new MoviesHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {

        holder.tvTitle.setText(movieList.getLogin());
//        holder.tvOverview.setText(movieList.getBlog());
//        holder.tvReleaseDate.setText(movieList.getName());

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MoviesHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvOverview,tvReleaseDate;
        ImageView ivMovie;

        public MoviesHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
//            tvOverview = (TextView) v.findViewById(R.id.tvOverView);
//            tvReleaseDate = (TextView) v.findViewById(R.id.tvReleaseDate);
        }
    }
}
