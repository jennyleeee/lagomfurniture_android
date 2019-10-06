package com.example.lagomfurniture.utils.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.model.Category;
import com.example.lagomfurniture.utils.interfaces.OnItemClickListener;

import java.util.List;

public class MainCategoryRcAdapter extends RecyclerView.Adapter<MainCategoryRcAdapter.ViewHolder> {
    private static final String TAG = "결";
    private static final int SELECT_ON_VIEW = 2;
    private static final int SELECT_OFF_VIEW = 1;

    private List<Category> items;
    private Context mContext;

    private OnItemClickListener listener;

    public MainCategoryRcAdapter(List<Category> item, Context context) {
        items = item;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getItemViewType() == 2) {
            return SELECT_ON_VIEW;
        } else {
            return SELECT_OFF_VIEW;
        }
    }

    @NonNull
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final int viewtype = items.get(position).getItemViewType();
        CategoryOffBindViewHolder(holder, position, viewtype);
        CategoryOnBindViewHolder(holder, position, viewtype);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void CategoryOnBindViewHolder(ViewHolder holder, int position, int viewtype) {
        if (viewtype == SELECT_ON_VIEW) {
            holder.category_image.setImageResource(items.get(position).getImage());
            holder.category_title.setText(items.get(position).getTitle());
        }
    }

    private void CategoryOffBindViewHolder(ViewHolder holder, int position, int viewtype) {
        if (viewtype == SELECT_OFF_VIEW) {
            holder.category_image.setImageResource(items.get(position).getImage());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView category_image;
        TextView category_title;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            category_title = itemView.findViewById(R.id.category_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(items.get(position),position);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
