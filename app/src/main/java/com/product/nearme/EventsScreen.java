package com.product.nearme;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.gson.Gson;
import com.product.nearme.adapter.CategoryListAdapter;
import com.product.nearme.cview.NeoGramMEditText;
import com.product.nearme.cview.TypefaceTextview;
import com.product.nearme.models.CategoryName;
import com.product.nearme.utils.constant;
import com.product.nearme.volley.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EventsScreen extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    static NeoGramMEditText txt_starttime, txt_endtime, edt_mon_time, edt_mon_time2, edt_t_time1, edt_t_time2, edt_w_time1, edt_w_time2, edt_th_time1, edt_th_time2, edt_f_time1, edt_f_time2, edt_s_time1, edt_s_time2, edt_sn_time1, edt_sn_time2,
            et_name, et_desc, et_location, et_address, et_mobile, et_website, et_email;

    TypefaceTextview txt_mon, txt_tue, txt_wed, txt_thu, txt_fri, txt_sat, txt_sun;
    Boolean mon = true, tue = true, wed = true, thu = true, fri = true, sat = true, sun = true;
    ArrayList<CategoryName> CategoryNameList;
    Spinner spr_category;
    String categoryName, categoryId, address, lat, lng;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_screen);

        init();
    }

    private void init() {
        txt_starttime = (NeoGramMEditText) findViewById(R.id.txt_starttime);
        txt_endtime = (NeoGramMEditText) findViewById(R.id.txt_endtime);
        edt_mon_time = (NeoGramMEditText) findViewById(R.id.edt_mon_time);
        edt_mon_time2 = (NeoGramMEditText) findViewById(R.id.edt_mon_time2);
        edt_t_time1 = (NeoGramMEditText) findViewById(R.id.edt_t_time1);
        edt_t_time2 = (NeoGramMEditText) findViewById(R.id.edt_t_time2);
        edt_w_time1 = (NeoGramMEditText) findViewById(R.id.edt_w_time1);
        edt_w_time2 = (NeoGramMEditText) findViewById(R.id.edt_w_time2);
        edt_th_time1 = (NeoGramMEditText) findViewById(R.id.edt_th_time1);
        edt_th_time2 = (NeoGramMEditText) findViewById(R.id.edt_th_time2);
        edt_f_time1 = (NeoGramMEditText) findViewById(R.id.edt_f_time1);
        edt_f_time2 = (NeoGramMEditText) findViewById(R.id.edt_f_time2);
        edt_s_time1 = (NeoGramMEditText) findViewById(R.id.edt_s_time1);
        edt_s_time2 = (NeoGramMEditText) findViewById(R.id.edt_s_time2);
        edt_sn_time1 = (NeoGramMEditText) findViewById(R.id.edt_sn_time1);
        edt_sn_time2 = (NeoGramMEditText) findViewById(R.id.edt_sn_time2);
        et_name = (NeoGramMEditText) findViewById(R.id.et_name);
        et_desc = (NeoGramMEditText) findViewById(R.id.et_desc);
        et_location = (NeoGramMEditText) findViewById(R.id.et_address);
        et_address = (NeoGramMEditText) findViewById(R.id.et_address);
        et_mobile = (NeoGramMEditText) findViewById(R.id.edt_number);
        et_website = (NeoGramMEditText) findViewById(R.id.edt_website);
        et_email = (NeoGramMEditText) findViewById(R.id.edt_email);
        spr_category = (Spinner) findViewById(R.id.spr_category);

        CategoryNameList = new ArrayList<>();

        txt_mon = (TypefaceTextview) findViewById(R.id.txt_mon);
        txt_tue = (TypefaceTextview) findViewById(R.id.txt_tue);
        txt_wed = (TypefaceTextview) findViewById(R.id.txt_wed);
        txt_thu = (TypefaceTextview) findViewById(R.id.txt_thu);
        txt_fri = (TypefaceTextview) findViewById(R.id.txt_fri);
        txt_sat = (TypefaceTextview) findViewById(R.id.txt_sat);
        txt_sun = (TypefaceTextview) findViewById(R.id.txt_sun);

        txt_starttime.setOnClickListener(this);
        txt_endtime.setOnClickListener(this);
        edt_mon_time.setOnClickListener(this);
        edt_mon_time2.setOnClickListener(this);
        edt_t_time1.setOnClickListener(this);
        edt_t_time2.setOnClickListener(this);
        edt_w_time1.setOnClickListener(this);
        edt_w_time2.setOnClickListener(this);
        edt_th_time1.setOnClickListener(this);
        edt_th_time2.setOnClickListener(this);
        edt_f_time1.setOnClickListener(this);
        edt_f_time2.setOnClickListener(this);
        edt_s_time1.setOnClickListener(this);
        edt_s_time2.setOnClickListener(this);
        edt_sn_time1.setOnClickListener(this);
        edt_sn_time2.setOnClickListener(this);

        et_address.setOnClickListener(this);
        findViewById(R.id.bt_applytoall).setOnClickListener(this);
        findViewById(R.id.txt_mon).setOnClickListener(this);
        findViewById(R.id.txt_tue).setOnClickListener(this);
        findViewById(R.id.txt_wed).setOnClickListener(this);
        findViewById(R.id.txt_thu).setOnClickListener(this);
        findViewById(R.id.txt_fri).setOnClickListener(this);
        findViewById(R.id.txt_sat).setOnClickListener(this);
        findViewById(R.id.txt_sun).setOnClickListener(this);

        findViewById(R.id.btn_finish).setOnClickListener(this);
        spr_category.setOnItemSelectedListener(this);

        findViewById(R.id.layout_timings).setVisibility(View.GONE);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equals("Always Available")) {
                        findViewById(R.id.layout_timings).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.layout_timings).setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        getspinnerdata();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_starttime:
                opentimepicker(txt_starttime);
                break;
            case R.id.txt_endtime:
                opentimepicker(txt_endtime);
                break;
            case R.id.edt_mon_time:
                if (mon = true) {
                    opentimepicker(edt_mon_time);
                }
                break;
            case R.id.edt_mon_time2:
                if (mon = true) {
                    opentimepicker(edt_mon_time2);
                }
                break;
            case R.id.edt_t_time1:
                if (tue = true) {
                    opentimepicker(edt_t_time1);
                }
                break;
            case R.id.edt_t_time2:
                if (tue = true) {
                    opentimepicker(edt_t_time2);
                }
                break;
            case R.id.edt_w_time1:
                if (wed = true) {
                    opentimepicker(edt_w_time1);
                }
                break;
            case R.id.edt_w_time2:
                if (wed = true) {
                    opentimepicker(edt_w_time2);
                }
                break;
            case R.id.edt_th_time1:
                if (thu = true) {
                    opentimepicker(edt_th_time1);
                }
                break;
            case R.id.edt_th_time2:
                if (thu = true) {
                    opentimepicker(edt_th_time2);
                }
                break;
            case R.id.edt_f_time1:
                if (fri = true) {
                    opentimepicker(edt_f_time1);
                }
                break;
            case R.id.edt_f_time2:
                if (fri = true) {
                    opentimepicker(edt_f_time2);
                }
                break;
            case R.id.edt_s_time1:
                if (sat = true) {
                    opentimepicker(edt_s_time1);
                }
                break;
            case R.id.edt_s_time2:
                if (sat = true) {
                    opentimepicker(edt_s_time2);
                }
                break;
            case R.id.edt_sn_time1:
                if (sun = true) {
                    opentimepicker(edt_sn_time1);
                }
                break;
            case R.id.edt_sn_time2:
                if (sun = true) {
                    opentimepicker(edt_sn_time2);
                }
                break;
            case R.id.bt_applytoall:
                applytoall();
                break;
            case R.id.txt_mon:
                Day_enable(txt_mon, 0);
                break;
            case R.id.txt_tue:
                Day_enable(txt_tue, 1);
                break;
            case R.id.txt_wed:
                Day_enable(txt_wed, 2);
                break;
            case R.id.txt_thu:
                Day_enable(txt_thu, 3);
                break;
            case R.id.txt_fri:
                Day_enable(txt_fri, 4);
                break;
            case R.id.txt_sat:
                Day_enable(txt_sat, 5);
                break;
            case R.id.txt_sun:
                Day_enable(txt_sun, 6);
                break;
            case R.id.btn_finish:
                Updatedata();
                break;
            case R.id.et_address:
                getplaces();
                break;
        }
    }

    private void getplaces() {
        int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .build();
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    private void opentimepicker(final NeoGramMEditText times) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(EventsScreen.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (selectedHour >= 12) {
                    times.setText(selectedHour + ":" + selectedMinute + " pm");
                } else {
                    times.setText(selectedHour + ":" + selectedMinute + " am");
                }

            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Google", "Place: " + place.getName());
                et_address.setText(place.getName() + " , " + place.getAddress());

                address = place.getName().toString() + "," + place.getAddress().toString();
                lat = String.valueOf(place.getLatLng().latitude);
                lng = String.valueOf(place.getLatLng().longitude);

                Log.e("lng", "#" + lng);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("Google", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void applytoall() {
        edt_mon_time.setText(txt_starttime.getText().toString());
        edt_t_time1.setText(txt_starttime.getText().toString());
        edt_w_time1.setText(txt_starttime.getText().toString());
        edt_th_time1.setText(txt_starttime.getText().toString());
        edt_f_time1.setText(txt_starttime.getText().toString());
        edt_s_time1.setText(txt_starttime.getText().toString());
        edt_sn_time1.setText(txt_starttime.getText().toString());

        edt_mon_time2.setText(txt_endtime.getText().toString());
        edt_t_time2.setText(txt_endtime.getText().toString());
        edt_w_time2.setText(txt_endtime.getText().toString());
        edt_th_time2.setText(txt_endtime.getText().toString());
        edt_f_time2.setText(txt_endtime.getText().toString());
        edt_s_time2.setText(txt_endtime.getText().toString());
        edt_sn_time2.setText(txt_endtime.getText().toString());
    }

    private void Day_enable(TypefaceTextview txt_mon, int i) {
        if (i == 0) {
            if (mon == true) {
                mon = false;
                txt_mon.setBackgroundResource(R.drawable.gray_textview_drawable);
            } else {
                mon = true;
                txt_mon.setBackgroundResource(R.drawable.blue_textview_drawable);
            }
        }

        if (i == 1) {
            if (tue == true) {
                tue = false;
                txt_tue.setBackgroundResource(R.drawable.gray_textview_drawable);
            } else {
                tue = true;
                txt_tue.setBackgroundResource(R.drawable.blue_textview_drawable);
            }
        }
        if (i == 2) {
            if (wed == true) {
                wed = false;
                txt_wed.setBackgroundResource(R.drawable.gray_textview_drawable);
            } else {
                wed = true;
                txt_wed.setBackgroundResource(R.drawable.blue_textview_drawable);
            }
        }
        if (i == 3) {
            if (thu == true) {
                thu = false;
                txt_thu.setBackgroundResource(R.drawable.gray_textview_drawable);
            } else {
                thu = true;
                txt_thu.setBackgroundResource(R.drawable.blue_textview_drawable);
            }
        }
        if (i == 4) {
            if (fri == true) {
                fri = false;
                txt_fri.setBackgroundResource(R.drawable.gray_textview_drawable);
            } else {
                fri = true;
                txt_fri.setBackgroundResource(R.drawable.blue_textview_drawable);
            }

        }
        if (i == 5) {
            if (sat == true) {
                sat = false;
                txt_sat.setBackgroundResource(R.drawable.gray_textview_drawable);
            } else {
                sat = true;
                txt_sat.setBackgroundResource(R.drawable.blue_textview_drawable);
            }
        }
        if (i == 6) {
            if (sun == true) {
                sun = false;
                txt_sun.setBackgroundResource(R.drawable.gray_textview_drawable);
            } else {
                sun = true;
                txt_sun.setBackgroundResource(R.drawable.blue_textview_drawable);
            }
        }
    }

    private void getspinnerdata() {
        final ProgressDialog progressDialog = new ProgressDialog(EventsScreen.this);
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/pbc/v1/pbcast/getallcatageoryLookup";

        StringRequest sr = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.e("Activeresponse»»»>", "#" + response);
                progressDialog.dismiss();
                categoryresponse(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                progressDialog.dismiss();
                Log.e(" response error is", "#" + response.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonObject = new HashMap<String, String>();

                return jsonObject;
            }
        };
//        queue.add(sr);
        AppController.getInstance().addToRequestQueue(sr, "string_req", EventsScreen.this);
    }

    private void categoryresponse(String response) {
        try {
            CategoryNameList.clear();

            JSONArray jsonArray = new JSONArray(response);
//            Log.e("jsonArray","#"+jsonArray.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String categoryid = jsonObject.getString("categoryid");
                String category_name = jsonObject.getString("category_name");

                CategoryNameList.add(new CategoryName(id, categoryid, category_name, "1"));

//                CategoryName category1 = new CategoryName(id, categoryid,category_name, "1");
//                CategoryNameList.add(category1);
            }

            if (CategoryNameList.size() >= 1) {
                doProcess(CategoryNameList);
            }
        } catch (Exception e) {

        }
    }

    private void doProcess(ArrayList<CategoryName> categoryNameList) {
        CategoryListAdapter adapter = new CategoryListAdapter(EventsScreen.this, categoryNameList, 0);
        spr_category.setAdapter(adapter);
    }

    private void Updatedata() {
        if (constant.isEmptyString(et_name.getText().toString())) {
            Toast.makeText(EventsScreen.this,"Enter name",Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_desc.getText().toString())) {
            Toast.makeText(EventsScreen.this,"Enter description",Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_address.getText().toString())) {
            Toast.makeText(EventsScreen.this,"Enter address",Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_mobile.getText().toString())) {
            Toast.makeText(EventsScreen.this,"Enter mobile",Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_email.getText().toString())) {
            Toast.makeText(EventsScreen.this,"Enter email",Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_website.getText().toString())) {
            Toast.makeText(EventsScreen.this,"Enter website",Toast.LENGTH_LONG).show();
        } else {
            try {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("createdBy", 1);
                jsonObj.put("updatedBy", 1);
                jsonObj.put("createdAt", "2018-09-19T18:30:00Z");
                jsonObj.put("updatedAt", "2018-09-19T18:30:00Z");
                jsonObj.put("userId", "2");
                jsonObj.put("interestName", et_name.getText().toString());
                jsonObj.put("descriptionAdv", et_desc.getText().toString());
                jsonObj.put("locationName", address);
                jsonObj.put("locationPinId", "12345");
                jsonObj.put("loc_lat", lat);
                jsonObj.put("loc_long", lng);
                jsonObj.put("postingCity", "12345");
                jsonObj.put("postStatus", "1");
                jsonObj.put("broadcastId", "1");
                jsonObj.put("categoryId", categoryId);
                jsonObj.put("categoryName", categoryName);
                jsonObj.put("isbroadcasting", "1");
                jsonObj.put("seenByCount", "1");
                jsonObj.put("endDate", "2018-09-19T18:30:00Z");

                JSONObject jsonAdd = new JSONObject();
                jsonAdd.put("categoryId", 100011);
                jsonAdd.put("categoryName", "MobileNumber");
                jsonAdd.put("attributeName", et_mobile.getText().toString());

                JSONObject jsonAdd2 = new JSONObject();
                jsonAdd2.put("categoryId", 100011);
                jsonAdd2.put("categoryName", "Email");
                jsonAdd2.put("attributeName", et_email.getText().toString());

                JSONObject jsonAdd3 = new JSONObject();
                jsonAdd3.put("categoryId", 100011);
                jsonAdd3.put("categoryName", "Website");
                jsonAdd3.put("attributeName", et_website.getText().toString());

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonAdd);
                jsonArray.put(jsonAdd2);
                jsonArray.put(jsonAdd3);

                jsonObj.put("attributes", jsonArray);
                jsonObj.put("isevent", true);


                Log.e("jsonObj", "#" + jsonObj.toString());

                uploaddata(jsonObj);

            } catch (Exception e) {

            }
        }
    }

    private void uploaddata(final JSONObject jsonObj) {
        final ProgressDialog progressDialog = new ProgressDialog(EventsScreen.this);
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/pbc/v1/pbcast/createads";

        StringRequest sr = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("uploaddata»»»>", "#" + response);
                progressDialog.dismiss();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                progressDialog.dismiss();
                Log.e(" response error is", "#" + response.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonObject = new HashMap<String, String>();
                jsonObject.put("createbeanmodel", jsonObj.toString());
                return jsonObject;
            }
        };
//        queue.add(sr);
        AppController.getInstance().addToRequestQueue(sr, "string_req", EventsScreen.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spr_category:
                CategoryName category = (CategoryName) adapterView.getItemAtPosition(i);
                categoryId = category.getCategoryid();
                categoryName = category.getCategoryName();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
