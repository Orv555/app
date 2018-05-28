package com.example.bayardomoraga.bars.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bayardomoraga.bars.R;
import com.example.bayardomoraga.bars.holder.BarsViewHolder;
import com.example.bayardomoraga.bars.model.BarsModel;

import java.util.List;

public class BarsAdapter extends RecyclerView.Adapter<BarsViewHolder> {
    private List<BarsModel> bars;

    public BarsAdapter(List<BarsModel>bars){
        this.bars = bars;
    }

    @Override
    public BarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bars, parent, false);
        return new BarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarsViewHolder holder, int position) {
        BarsModel bar =bars.get(position);
        holder.getId().setText(bar.getId());
        holder.getName().setText(bar.getName());
        holder.getDescription().setText(bar.getDescription());
        holder.getAddress().setText(bar.getAddress());
        holder.getType().setText(bar.getType());
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return bars.size();
    }
}
