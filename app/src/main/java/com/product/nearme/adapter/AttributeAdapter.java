package com.product.nearme.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.product.nearme.R;

import java.util.ArrayList;

public class AttributeAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> attribute_list;
    private LayoutInflater inflater;

    public AttributeAdapter(Context mContext, ArrayList<String> attribute_list) {
        this.mContext = mContext;
        this.attribute_list = attribute_list;
        inflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return attribute_list.size();
    }

    @Override
    public Object getItem(int position) {
        return attribute_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AttributeAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_spinner_item, null);
            holder = new AttributeAdapter.ViewHolder();
            holder.txt_spinner = (TextView) convertView
                    .findViewById(R.id.txt_spinner);
            convertView.setTag(holder);
        } else {
            holder = (AttributeAdapter.ViewHolder) convertView.getTag();
        }

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/SF-Pro-Display-Regular.otf");
        holder.txt_spinner.setTypeface(tf);

        holder.txt_spinner.setText((attribute_list.get(position)));


        return convertView;
    }

    class ViewHolder {
        TextView txt_spinner;
    }
}
