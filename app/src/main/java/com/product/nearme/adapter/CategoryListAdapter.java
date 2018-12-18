package com.product.nearme.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.product.nearme.R;
import com.product.nearme.models.CategoryName;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter implements SpinnerAdapter {
    Context context;
    int flag;
    List<CategoryName> data = new ArrayList<CategoryName>();

    public CategoryListAdapter(Context context, ArrayList<CategoryName> data, int flag) {
        this.context = context;
        this.data = data;
        this.flag = flag;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryName obj = data.get(position);
        TextView textView = (TextView) View.inflate(context, android.R.layout.simple_spinner_item, null);
        textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), 30, textView.getPaddingBottom());
//        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.otf");
//        textView.setTypeface(font);
        textView.setText(obj.getCategoryName());
        textView.setTextColor(Color.parseColor("#000000"));

        return textView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        CategoryName obj = data.get(position);
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row_spinner, parent, false);
            holder = new Holder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.txtName.setText(obj.getCategoryName());
        return convertView;
    }

    static class Holder {
        TextView txtName;
    }
}


