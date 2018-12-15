package com.watchingTVmobile.watchingTV.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.activities.FilmDetailsActivity;
import com.watchingTVmobile.watchingTV.network.films.FilmDescrip;
import com.watchingTVmobile.watchingTV.utils.Constants;
import com.watchingTVmobile.watchingTV.utils.Favoris;

import java.util.List;

public class FilmsDescripSmallAdapter extends RecyclerView.Adapter<FilmsDescripSmallAdapter.FilmViewHolder> {

    private Context mContext;
    private List<FilmDescrip> mFilms;

    public FilmsDescripSmallAdapter(Context context, List<FilmDescrip> films) {
        mContext = context;
        mFilms = films;
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilmViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_show_small, parent, false));
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {

        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mFilms.get(position).getPosterPath())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.filmPosterImageView);

        if (mFilms.get(position).getTitle() != null)
            holder.filmTitleTextView.setText(mFilms.get(position).getTitle());
        else
            holder.filmTitleTextView.setText("");

        if (Favoris.isMovieFav(mContext, mFilms.get(position).getId())) {
            holder.filmFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp);
            holder.filmFavImageButton.setEnabled(false);
        } else {
            holder.filmFavImageButton.setImageResource(R.mipmap.ic_favorite_border_black_18dp);
            holder.filmFavImageButton.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return mFilms.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {

        public CardView filmCard;
        public ImageView filmPosterImageView;
        public TextView filmTitleTextView;
        public ImageButton filmFavImageButton;


        public FilmViewHolder(View itemView) {
            super(itemView);
            filmCard = (CardView) itemView.findViewById(R.id.card_view_show_card);
            filmPosterImageView = (ImageView) itemView.findViewById(R.id.image_view_show_card);
            filmTitleTextView = (TextView) itemView.findViewById(R.id.text_view_title_show_card);
            filmFavImageButton = (ImageButton) itemView.findViewById(R.id.image_button_fav_show_card);

            filmPosterImageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            filmPosterImageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            filmCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FilmDetailsActivity.class);
                    intent.putExtra(Constants.MOVIE_ID, mFilms.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });

            filmFavImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    Favoris.addMovieToFav(mContext, mFilms.get(getAdapterPosition()).getId(), mFilms.get(getAdapterPosition()).getPosterPath(), mFilms.get(getAdapterPosition()).getTitle());
                    filmFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp);
                    filmFavImageButton.setEnabled(false);
                }
            });
        }
    }

}
