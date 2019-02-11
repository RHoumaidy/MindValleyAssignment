package com.mindvalley.raafat.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindvalley.raafat.Cache.ImageLoader;
import com.mindvalley.raafat.Modules.Photo;
import com.mindvalley.raafat.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Raafat Alhoumaidy on 2/9/2019 @10:29 AM.
 */
public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Photo> data;
    private Context ctx;
    private LayoutInflater inflater;

    public PhotosAdapter(Context _ctx, List<Photo> _data) {
        this.ctx = _ctx;
        this.data = _data;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerView.ViewHolder(inflater.inflate(R.layout.item_pinboard_layout, viewGroup, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final ImageView thumbImgV = viewHolder.itemView.findViewById(R.id.thumbImgV);
        final CircleImageView profileImgV = viewHolder.itemView.findViewById(R.id.userProfileImgV);
        final TextView userNameTxtV = viewHolder.itemView.findViewById(R.id.userNameTxtV);
        final TextView likesTxtV = viewHolder.itemView.findViewById(R.id.likesTxtV);


        Photo currItem = data.get(viewHolder.getAdapterPosition());
        new ImageLoader().load(currItem.getUrls().getThumb())
                .into(thumbImgV)
                .execute();
        new ImageLoader().load(currItem.getUser().getProfileImages().getMedium())
                .into(profileImgV)
                .execute();

        userNameTxtV.setText(currItem.getUser().getName());
        likesTxtV.setText(currItem.getLikes() + "");
        likesTxtV.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_favorite_border_white_24dp, 0, 0, 0);
        if (currItem.isLikedByUser()) {
            likesTxtV.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_favorite_red_24dp, 0, 0, 0);
        }

        thumbImgV.setTransitionName(ctx.getString(R.string.thumb_trans)+position);
        profileImgV.setTransitionName(ctx.getString(R.string.profile_trans)+position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
