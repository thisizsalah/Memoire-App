package com.madi.msdztest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArtisanListRecyclerViewAdapter extends RecyclerView.Adapter<ArtisanListRecyclerViewAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<ListData> listData;

    public ArtisanListRecyclerViewAdapter(Context context, ArrayList<ListData> listData) {
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
    public void onBindViewHolder(@NonNull ArtisanListRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(listData.get(position).getName());
        holder.tvCat.setText(listData.get(position).getCategorie());
        holder.tvCity.setText(listData.get(position).getCity());
        holder.imageView.setImageResource(listData.get(position).getImage());




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
