package com.dagnerchuman.minimarket.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dagnerchuman.minimarket.R;
import com.dagnerchuman.minimarket.entity.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private List<ImageItem> mSliderItems = new ArrayList<>();

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageItem sliderItem = mSliderItems.get(position);

        holder.textView.setText(sliderItem.getTitulo());
        holder.textView.setTextSize(16);
        holder.textView.setTextColor(Color.WHITE);
        Glide.with(holder.itemView)
                .load(sliderItem.getImagen())
                .fitCenter()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mSliderItems.size();
    }

    public void updateItem(List<ImageItem> lista) {
        mSliderItems.clear();
        mSliderItems.addAll(lista);
        notifyDataSetChanged();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_auto_image_slider);
            textView = itemView.findViewById(R.id.tv_auto_image_slider);
        }
    }
}
