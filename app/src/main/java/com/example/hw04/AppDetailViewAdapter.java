package com.example.hw04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppDetailViewAdapter extends RecyclerView.Adapter<AppDetailViewAdapter.AppDetailViewHolder>{
    ArrayList<String> Genres;
    public AppDetailViewAdapter(ArrayList<String> Genres){
        this.Genres = Genres;
    }
    @NonNull
    @Override

    public AppDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_detail_layout,parent,false);
        AppDetailViewAdapter.AppDetailViewHolder viewHolder = new AppDetailViewAdapter.AppDetailViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppDetailViewHolder holder, int position) {
        String genre_name = Genres.get(position);
        holder.genre.setText(genre_name);

    }

    @Override
    public int getItemCount() {
        return Genres.size();
    }

    public static class AppDetailViewHolder extends RecyclerView.ViewHolder{
        TextView genre;
        public AppDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            genre = itemView.findViewById(R.id.textView_genre_row);
        }
    }
}
