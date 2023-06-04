package com.madi.msdztest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.madi.msdztest.models.Artisan;

import java.util.List;

public class ArtisanListRecyclerViewAdapter extends RecyclerView.Adapter<ArtisanListRecyclerViewAdapter.MyViewHolder> {
    private final Context context;
    private final List<Artisan> listData;

    public ArtisanListRecyclerViewAdapter(Context context, List<Artisan> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int Position) {
        holder.tvName.setText(listData.get(holder.getAdapterPosition()).getNom() + " " + listData.get(holder.getAdapterPosition()).getPrénom());
        holder.tvCat.setText(listData.get(holder.getAdapterPosition()).getCatégorie());
        holder.tvCity.setText(listData.get(holder.getAdapterPosition()).getWilaya());
        //holder.imageView.setImageResource(listData.get(position).getImage());

        if ( listData.get(holder.getAdapterPosition()).getImageProfile() != null) {
            String imageUrl = listData.get(holder.getAdapterPosition()).getImageProfile();
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL); // Caches the image

            Glide.with(context)
                    .load(imageUrl)
                    .apply(requestOptions)
                    .into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Artisan artisan = listData.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, SelectedArtisanActivity.class);
                intent.putExtra("artisan_id",artisan.getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName, tvCat, tvCity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView5);
            tvName = itemView.findViewById(R.id.textViewName);
            tvCat = itemView.findViewById(R.id.textView16);
            tvCity = itemView.findViewById(R.id.textView17);


        }
    }
}
