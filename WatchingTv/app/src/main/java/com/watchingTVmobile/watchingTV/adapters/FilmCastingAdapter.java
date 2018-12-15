package com.watchingTVmobile.watchingTV.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.network.films.FilmCastingDescrip;
import com.watchingTVmobile.watchingTV.utils.Constants;

import java.util.List;

public class FilmCastingAdapter extends RecyclerView.Adapter<FilmCastingAdapter.CastingViewHolder> {
    private Context mContext;
    private List<FilmCastingDescrip> mCasting;

    public FilmCastingAdapter(Context mContext, List<FilmCastingDescrip> mCasting) {
        this.mContext = mContext;
        this.mCasting = mCasting;
    }

    @Override
    public CastingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CastingViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cast, parent, false));
    }

    @Override
    public void onBindViewHolder(CastingViewHolder holder, int position) {

        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mCasting.get(position).getProfilePath())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.castingImageView);

        if (mCasting.get(position).getName() != null)
            holder.nameTextView.setText(mCasting.get(position).getName());
        else
            holder.nameTextView.setText("");

        if (mCasting.get(position).getCharacter() != null)
            holder.characterTextView.setText(mCasting.get(position).getCharacter());
        else
            holder.characterTextView.setText("");

    }

    @Override
    public int getItemCount() {
        return mCasting.size();
    }

    public class CastingViewHolder extends RecyclerView.ViewHolder {
        public ImageView castingImageView;
        public TextView nameTextView;
        public TextView characterTextView;

        public CastingViewHolder(View itemView) {
            super(itemView);
            castingImageView = (ImageView) itemView.findViewById(R.id.image_view_cast);
            nameTextView = (TextView) itemView.findViewById(R.id.text_view_cast_name);
            characterTextView = (TextView) itemView.findViewById(R.id.text_view_cast_as);
        }
    }
}
