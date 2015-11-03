package com.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.R;

public class DrawerListAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private Context context;
    private String menu[];
    public DrawerListAdapter(Context context, String menu[]) {
        this.context = context;
        this.menu = menu;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return menu.length;
    }

    @Override
    public Object getItem(int position) {
        return menu[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_drawer, null);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        holder.title.setText(menu[position]);

        return view;
    }

    public static class ViewHolder {
        TextView title;
    }
}