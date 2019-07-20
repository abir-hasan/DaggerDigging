package me.abir.daggerdigging;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ListAdapter;
import android.util.Log;
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

public class TvAdapter extends ListAdapter<Result, TvAdapter.TvViewHolder>/*RecyclerView.Adapter<TvAdapter.TvViewHolder>*/ {
    private static final String TAG = "TvAdapter";
    private Context context;
    private Picasso picasso;
    private List<Result> results = new ArrayList<>();
    private List<Result> copyList = new ArrayList<>();

    public TvAdapter(Context context, Picasso picasso) {
        super(UserDiffCallback);
        this.context = context;
        this.picasso = picasso;
    }

    @Override
    public TvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_tv, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvViewHolder tvViewHolder, int position) {
        Log.e(TAG, "onBindViewHolder()  position = [" + position + "]");
        //TvViewHolder tvViewHolder = (TvViewHolder) holder;
        Result result = getItem(position);
        //Result result = results.get(position);

        tvViewHolder.tvName.setText(result.getPageNo() + result.getName());
        tvViewHolder.tvYear.setText(result.getFirstAirDate());
        tvViewHolder.tvRating.setText(result.getVoteAverage().toString());
        tvViewHolder.tvCount.setText(result.getVoteCount().toString());

        String posterPath = TMDbService.Companion.getPOSTER_URL() + result.getPosterPath();
        Log.i(TAG, "onBindViewHolder: posterPath: "+posterPath);
        Picasso.get().load(posterPath)
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_launcher_foreground)
                .into(tvViewHolder.ivPoster);

    }

    /*@Override
    public int getItemCount() {
        return results.size();
    }*/


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
        Log.w(TAG, "setTVData() called with: resultList = [" + resultList.size() + "] prev: " + super.getItemCount());
        //this.results.clear();
        //this.copyList.clear();
        //results.addAll(resultList);
        //copyList.addAll(resultList);
        //notifyDataSetChanged();


        results.addAll(resultList);
        submitList(results);

    }

    public void filter(String queryText) {
        results.clear();

        if (queryText.isEmpty()) {
            results.addAll(copyList);
        } else {

            for (Result result : copyList) {
                if (result.getName().toLowerCase().contains(queryText.toLowerCase())) {
                    results.add(result);
                }
            }
        }
        //notifyDataSetChanged();
        submitList(results);
    }

    private static DiffUtil.ItemCallback<Result> UserDiffCallback = new DiffUtil.ItemCallback<Result>() {
        @Override
        public boolean areItemsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            Log.i(TAG, "areItemsTheSame() called with: oldItem = [" + oldItem.getId() +
                    "], newItem = [" + newItem.getId() + "] res: " + oldItem.getId().equals(newItem.getId())
                    + " same? " + oldItem.equals(newItem));
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            Log.w(TAG, "areContentsTheSame() called with: oldItem = [" + oldItem + "], newItem = [" + newItem + "]");
            return oldItem.equals(newItem);
        }
    };

}
