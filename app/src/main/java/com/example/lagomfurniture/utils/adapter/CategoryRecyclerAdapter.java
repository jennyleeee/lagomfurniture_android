package com.example.lagomfurniture.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.model.Category;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {
    private static final int SELECT_ON_VIEW = 1;
    private static final int SELECT_OFF_VIEW = 0;
    private List<Category> categories;

    public CategoryRecyclerAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int getItemViewType(int position) {
        if (categories.get(position).getItemViewType() == 1) {
            return SELECT_ON_VIEW;
        } else {
            return SELECT_OFF_VIEW;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == SELECT_ON_VIEW) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category_on, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category_off, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewtype = categories.get(position).getItemViewType();
        CategoryOffBindViewHolder(holder, position, viewtype);
        CategoryOnBindViewHolder(holder, position, viewtype);
    }

    private void CategoryOnBindViewHolder(ViewHolder holder, int position, int viewtype) {
        if (viewtype == SELECT_ON_VIEW) {
            holder.category_image.setImageResource(categories.get(position).getImage());
            holder.category_title.setText(categories.get(position).getTitle());
        }
    }

    private void CategoryOffBindViewHolder(ViewHolder holder, int position, int viewtype) {
        if (viewtype == SELECT_OFF_VIEW) {
            holder.category_image.setImageResource(categories.get(position).getImage());
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView category_image;
        TextView category_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            category_title = itemView.findViewById(R.id.category_title);
        }
    }
}
