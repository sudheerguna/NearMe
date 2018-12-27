package com.product.nearme.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.product.nearme.R;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_custom_infowindow, null);

        LinearLayout layoutfull = view.findViewById(R.id.layoutfull);
        ImageView img_arrow = view.findViewById(R.id.img_arrow);
        TextView name_tv = view.findViewById(R.id.name);
        TextView details_tv = view.findViewById(R.id.details);
        ImageView img = view.findViewById(R.id.pic);

        TextView hotel_tv = view.findViewById(R.id.hotels);
        TextView food_tv = view.findViewById(R.id.food);
        TextView transport_tv = view.findViewById(R.id.transport);


        try{
            name_tv.setText(marker.getTitle());
            details_tv.setText(marker.getSnippet());

            InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

            int imageId = context.getResources().getIdentifier(infoWindowData.getImage().toLowerCase(),
                    "drawable", context.getPackageName());
            img.setImageResource(imageId);

            hotel_tv.setText(infoWindowData.getHotel());
            food_tv.setText(infoWindowData.getFood());
            transport_tv.setText(infoWindowData.getTransport());

        }catch (Exception e){
            img_arrow.setVisibility(View.GONE);
            food_tv.setVisibility(View.GONE);
            transport_tv.setVisibility(View.GONE);
        }


        return view;
    }
}
