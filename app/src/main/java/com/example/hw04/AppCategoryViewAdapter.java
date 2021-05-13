package com.example.hw04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppCategoryViewAdapter extends RecyclerView.Adapter<AppCategoryViewAdapter.CategoryViewHolder>{
    ArrayList<String> Categories;
    AppCategoryViewAdapterInterface IListener;
    public AppCategoryViewAdapter(ArrayList<String> Data, AppCategoryViewAdapterInterface IListener){
        this.Categories = Data;
        this.IListener = IListener;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_category_layout,parent,false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(view,IListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = Categories.get(position);
        holder.category_name.setText(category);
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView category_name;
        int position;
        AppCategoryViewAdapterInterface IListener;
        public CategoryViewHolder(@NonNull View itemView, AppCategoryViewAdapterInterface IListener) {
            super(itemView);
            this.IListener = IListener;
            category_name = itemView.findViewById(R.id.textView_category_row);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IListener.get_app_list(category_name.getText().toString());
                }
            });
        }
    }


    interface AppCategoryViewAdapterInterface {
        void get_app_list(String name);
    }
}
