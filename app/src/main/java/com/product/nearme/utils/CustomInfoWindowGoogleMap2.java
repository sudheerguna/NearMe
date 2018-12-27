package com.product.nearme.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.product.nearme.R;
import com.product.nearme.cview.TypefaceTextview;

public class CustomInfoWindowGoogleMap2 implements GoogleMap.InfoWindowAdapter {

    private Context context;
    String cityname;

    public CustomInfoWindowGoogleMap2(Context ctx, String cityname){
        context = ctx;
        this.cityname = cityname;
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
        TypefaceTextview name_tv = view.findViewById(R.id.name);
        TypefaceTextview details_tv = view.findViewById(R.id.details);
        ImageView img = view.findViewById(R.id.pic);

        TypefaceTextview hotel_tv = view.findViewById(R.id.hotels);
        TypefaceTextview food_tv = view.findViewById(R.id.food);
        TypefaceTextview transport_tv = view.findViewById(R.id.transport);
        TypefaceTextview txt_city = view.findViewById(R.id.txt_city);


        try{
//            name_tv.setText(marker.getTitle());
//            details_tv.setText(marker.getSnippet());

//            InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

//            int imageId = context.getResources().getIdentifier(infoWindowData.getImage().toLowerCase(),
//                    "drawable", context.getPackageName());
            img.setVisibility(View.GONE);
            hotel_tv.setVisibility(View.GONE);

            txt_city.setVisibility(View.VISIBLE);
            txt_city.setText(cityname);

            img_arrow.setVisibility(View.GONE);
            food_tv.setVisibility(View.GONE);
            transport_tv.setVisibility(View.GONE);
        }catch (Exception e){
            Log.e("getMessage", "#"+e.getMessage() );
        }


        return view;
    }
}
