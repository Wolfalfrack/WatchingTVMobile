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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.activities.FilmDetailsActivity;
import com.watchingTVmobile.watchingTV.network.films.FilmDescrip;
import com.watchingTVmobile.watchingTV.utils.Constants;
import com.watchingTVmobile.watchingTV.utils.Favoris;
import com.watchingTVmobile.watchingTV.utils.FilmsGenres;

import java.util.List;


public class FilmsDescripLargeAdapter extends RecyclerView.Adapter<FilmsDescripLargeAdapter.FilmViewHolder> {

    private Context mContext;
    private List<FilmDescrip> mMovies;

    public FilmsDescripLargeAdapter(Context context, List<FilmDescrip> movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilmViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_show_large, parent, false));
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {

        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_780 + mMovies.get(position).getBackdropPath())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.filmPosterImageView);

        if (mMovies.get(position).getTitle() != null)
            holder.filmTitleTextView.setText(mMovies.get(position).getTitle());
        else
            holder.filmTitleTextView.setText("");

        if (mMovies.get(position).getVoteAverage() != null && mMovies.get(position).getVoteAverage() > 0) {
            holder.filmRatingTextView.setVisibility(View.VISIBLE);
            holder.filmRatingTextView.setText(String.format("%.1f", mMovies.get(position).getVoteAverage()) + Constants.RATING_SYMBOL);
        } else {
            holder.filmRatingTextView.setVisibility(View.GONE);
        }

        setGenres(holder, mMovies.get(position));

        if (Favoris.isMovieFav(mContext, mMovies.get(position).getId())) {
            holder.filmFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp);
            holder.filmFavImageButton.setEnabled(false);
        } else {
            holder.filmFavImageButton.setImageResource(R.mipmap.ic_favorite_border_black_18dp);
            holder.filmFavImageButton.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    private void setGenres(FilmViewHolder holder, FilmDescrip movie) {
        String genreString = "";
        for (int i = 0; i < movie.getGenreIds().size(); i++) {
            if (movie.getGenreIds().get(i) == null) continue;
            if (FilmsGenres.getGenreName(movie.getGenreIds().get(i)) == null) continue;
            genreString += FilmsGenres.getGenreName(movie.getGenreIds().get(i)) + ", ";
        }
        if (!genreString.isEmpty())
            holder.filmGenreTextView.setText(genreString.substring(0, genreString.length() - 2));
        else
            holder.filmGenreTextView.setText("");
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {

        public CardView filmCard;
        public RelativeLayout imageLayout;
        public ImageView filmPosterImageView;
        public TextView filmTitleTextView;
        public TextView filmRatingTextView;
        public TextView filmGenreTextView;
        public ImageButton filmFavImageButton;


        public FilmViewHolder(View itemView) {
            super(itemView);
            filmCard = (CardView) itemView.findViewById(R.id.card_view_show_card);
            imageLayout = (RelativeLayout) itemView.findViewById(R.id.image_layout_show_card);
            filmPosterImageView = (ImageView) itemView.findViewById(R.id.image_view_show_card);
            filmTitleTextView = (TextView) itemView.findViewById(R.id.text_view_title_show_card);
            filmRatingTextView = (TextView) itemView.findViewById(R.id.text_view_rating_show_card);
            filmGenreTextView = (TextView) itemView.findViewById(R.id.text_view_genre_show_card);
            filmFavImageButton = (ImageButton) itemView.findViewById(R.id.image_button_fav_show_card);

            imageLayout.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.9);
            imageLayout.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.9) / 1.77);

            filmCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FilmDetailsActivity.class);
                    intent.putExtra(Constants.MOVIE_ID, mMovies.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });

            filmFavImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    Favoris.addMovieToFav(mContext, mMovies.get(getAdapterPosition()).getId(), mMovies.get(getAdapterPosition()).getPosterPath(), mMovies.get(getAdapterPosition()).getTitle());
                    filmFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp);
                    filmFavImageButton.setEnabled(false);
                }
            });
        }
    }

}
