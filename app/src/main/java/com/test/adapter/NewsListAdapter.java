package com.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.DetailActivity;
import com.test.R;
import com.test.model.Result;

import java.util.List;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> implements View.OnClickListener {
    private List<Result> list;
    private LayoutInflater inflater;
    private Context ctx;

    public NewsListAdapter(List<Result> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public void setArray(List<Result> list){
        this.list.addAll(list);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ctx, DetailActivity.class);
        int position = Integer.parseInt(v.getTag().toString());
        Result product = list.get(position);
        intent.putExtra("title", product.getTitle());
        intent.putExtra("url", product.getUrl());
        ctx.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDesc;
        TextView txtDate;
        ImageView img;
        View view;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtDesc = (TextView) v.findViewById(R.id.txtDesc);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            img = (ImageView) v.findViewById(R.id.img);
            view = v;
        }
    }


    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result product = list.get(position);

        holder.txtTitle.setText(product.getTitle());
        if(product.getDesc() != null) {
            holder.txtDesc.setText(product.getDesc());
        }else{
            holder.txtDesc.setText("");
        }
        holder.view.setTag(position);
        if (product.getImageUrl() != null && ! product.getImageUrl().equals("")) {
            Picasso.with(ctx).load(product.getImageUrl()).error(R.drawable.place_holder).into(holder.img);
            holder.img.setVisibility(View.VISIBLE);
        }else{
            holder.img.setVisibility(View.GONE);
            holder.img.setImageResource(R.drawable.place_holder);
        }

        String date = product.getUpdatedDate();
        String byLine = product.getByLine();
        if(byLine != null && !byLine.equals("")){
            byLine = " - " + byLine;
        }else{
            byLine = "";
        }

        if(date != null){
            holder.txtDate.setText(date + byLine);
        }else{
            holder.txtDate.setText(byLine);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}