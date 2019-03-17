package com.mindvalley.raafat.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindvalley.raafat.Models.Category;
import com.mindvalley.raafat.R;

import java.util.List;

/**
 * Created by Raafat Alhoumaidy on 2/9/2019 @11:49 AM.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category> data;
    private Context ctx;
    private LayoutInflater inflater;

    public CategoriesAdapter(Context _ctx,List<Category> _data){
        this.ctx = _ctx;
        this.data  = _data;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerView.ViewHolder(inflater.inflate(R.layout.item_category_layout,viewGroup,false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TextView categoryTitleTxtV = ((TextView) viewHolder.itemView);

        categoryTitleTxtV.setText(data.get(viewHolder.getAdapterPosition()).getTitle());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
