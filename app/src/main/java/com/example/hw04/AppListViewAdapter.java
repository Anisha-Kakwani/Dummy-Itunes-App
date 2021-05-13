package com.example.hw04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppListViewAdapter extends RecyclerView.Adapter<AppListViewAdapter.ListViewHolder> {
    ArrayList<DataServices.App> Lists;
    AppListViewAdapter.AppListViewAdapterInterface AppListListener;

    public AppListViewAdapter(ArrayList<DataServices.App> Data,AppListViewAdapterInterface AppListListener){
        this.Lists = Data;
        this.AppListListener = AppListListener;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_layout,parent,false);
        AppListViewAdapter.ListViewHolder viewHolder = new AppListViewAdapter.ListViewHolder(view,AppListListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        DataServices.App SelectedApp = Lists.get(position);
        holder.app_name.setText(SelectedApp.name);
        holder.artist_name.setText(SelectedApp.artistName);
        holder.release_date.setText(SelectedApp.releaseDate);
        holder.position = position;
        holder.app = SelectedApp;
    }

    @Override
    public int getItemCount() {
        return Lists.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        TextView artist_name,app_name,release_date;
        int position;
        DataServices.App app;
        AppListViewAdapter.AppListViewAdapterInterface AppListListener;
        public ListViewHolder(@NonNull View itemView, AppListViewAdapterInterface AppListListener) {
            super(itemView);
            this.AppListListener = AppListListener;
            artist_name = itemView.findViewById(R.id.textViewArtistName);
            app_name = itemView.findViewById(R.id.textViewAppName);
            release_date = itemView.findViewById(R.id.textViewReleaseDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppListListener.get_app_details(app);
                }
            });
        }
    }

    interface  AppListViewAdapterInterface{
        void get_app_details(DataServices.App app);
    }
}
