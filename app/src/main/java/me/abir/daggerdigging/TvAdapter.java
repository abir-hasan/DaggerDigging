package me.abir.daggerdigging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.abir.daggerdigging.models.Result;
import me.abir.daggerdigging.network.TMDbService;

/**
 * Created by Abir on 02-Jan-18.
 */

public class TvAdapter extends RecyclerView.Adapter {

    private Context context;
    private Picasso picasso;
    private List<Result> results = new ArrayList<>();

    public TvAdapter(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_tv, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TvViewHolder tvViewHolder = (TvViewHolder) holder;
        Result result = results.get(position);

        tvViewHolder.tvName.setText(result.getName());
        tvViewHolder.tvYear.setText(result.getFirstAirDate());
        tvViewHolder.tvRating.setText(result.getVoteAverage().toString());
        tvViewHolder.tvCount.setText(result.getVoteCount().toString());

        String posterPath = TMDbService.POSTER_URL + result.getPosterPath();
        picasso.with(context).load(posterPath)
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_launcher_foreground)
                .into(tvViewHolder.ivPoster);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    class TvViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPoster;
        TextView tvName;
        TextView tvYear;
        TextView tvRating;
        TextView tvCount;

        public TvViewHolder(View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvName = itemView.findViewById(R.id.tvName);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCount = itemView.findViewById(R.id.tvCount);
        }
    }

    public void setTVData(List<Result> resultList) {
        this.results.clear();
        results.addAll(resultList);
        notifyDataSetChanged();
    }
}
