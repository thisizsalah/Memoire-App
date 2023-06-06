package com.madi.msdztest;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapterCharger extends RecyclerView.Adapter<RecyclerAdapterCharger.ViewHolder> {

    private final ArrayList<Uri> uriArrayList;
    private final Context context;



    public RecyclerAdapterCharger(ArrayList<Uri> uriArrayList, Context context) {
        this.uriArrayList = uriArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerAdapterCharger.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.custom_single_image,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterCharger.ViewHolder holder, int position) {
        Glide.with(context).load(uriArrayList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageCharger);
        }
    }
}
