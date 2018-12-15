package com.watchingTVmobile.watchingTV.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.activities.FilmDetailsActivity;
import com.watchingTVmobile.watchingTV.activities.SerieDetailsActivity;
import com.watchingTVmobile.watchingTV.network.recherche.RechercheResultat;
import com.watchingTVmobile.watchingTV.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RechercheResultatAdapter extends RecyclerView.Adapter<RechercheResultatAdapter.ResultatViewHolder> {

    private Context mContext;
    private List<RechercheResultat> mRechercheResultat;

    public RechercheResultatAdapter(Context mContext, List<RechercheResultat> mRechercheResultat) {
        this.mContext = mContext;
        this.mRechercheResultat = mRechercheResultat;
    }

    @Override
    public ResultatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResultatViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recherche_resultat, parent, false));
    }

    @Override
    public void onBindViewHolder(final ResultatViewHolder holder, int position) {

        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mRechercheResultat.get(position).getPosterPath())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.posterImageView);

        if (mRechercheResultat.get(position).getName() != null && !mRechercheResultat.get(position).getName().trim().isEmpty())
            holder.nameTextView.setText(mRechercheResultat.get(position).getName());
        else
            holder.nameTextView.setText("");

        if (mRechercheResultat.get(position).getMediaType() != null && mRechercheResultat.get(position).getMediaType().equals("movie"))
            holder.mediaTypeTextView.setText(R.string.film);
        else if (mRechercheResultat.get(position).getMediaType() != null && mRechercheResultat.get(position).getMediaType().equals("tv"))
            holder.mediaTypeTextView.setText(R.string.Série);
        else
            holder.mediaTypeTextView.setText("");

        if (mRechercheResultat.get(position).getOverview() != null && !mRechercheResultat.get(position).getOverview().trim().isEmpty())
            holder.overviewTextView.setText(mRechercheResultat.get(position).getOverview());
        else
            holder.overviewTextView.setText("");

        if (mRechercheResultat.get(position).getReleaseDate() != null && !mRechercheResultat.get(position).getReleaseDate().trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date releaseDate = sdf1.parse(mRechercheResultat.get(position).getReleaseDate());
                holder.yearTextView.setText(sdf2.format(releaseDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.yearTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mRechercheResultat.size();
    }

    public class ResultatViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView posterImageView;
        public TextView nameTextView;
        public TextView mediaTypeTextView;
        public TextView overviewTextView;
        public TextView yearTextView;

        public ResultatViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_search);
            posterImageView = (ImageView) itemView.findViewById(R.id.image_view_poster_search);
            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name_search);
            mediaTypeTextView = (TextView) itemView.findViewById(R.id.text_view_media_type_search);
            overviewTextView = (TextView) itemView.findViewById(R.id.text_view_overview_search);
            yearTextView = (TextView) itemView.findViewById(R.id.text_view_year_search);

            posterImageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            posterImageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRechercheResultat.get(getAdapterPosition()).getMediaType().equals("movie")) {
                        Intent intent = new Intent(mContext, FilmDetailsActivity.class);
                        intent.putExtra(Constants.MOVIE_ID, mRechercheResultat.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    } else if (mRechercheResultat.get(getAdapterPosition()).getMediaType().equals("tv")) {
                        Intent intent = new Intent(mContext, SerieDetailsActivity.class);
                        intent.putExtra(Constants.TV_SHOW_ID, mRechercheResultat.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    }
                }
            });

        }
    }
}
