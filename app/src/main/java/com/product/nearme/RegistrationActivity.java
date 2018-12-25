package com.product.nearme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.product.nearme.adapter.AttributeAdapter;
import com.product.nearme.adapter.CategoryListAdapter;
import com.product.nearme.cview.NeoGramMEditText;
import com.product.nearme.cview.TypefaceTextview;
import com.product.nearme.models.CategoryName;
import com.product.nearme.utils.constant;
import com.product.nearme.volley.AppController;
import com.product.nearme.volley.MyJsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    NeoGramMEditText et_Name, et_Email, et_pwd, et_cnfm_pwd, et_mobile,et_address;
    static NeoGramMEditText et_date;
    Spinner txt_gender, spr_category;;
    private static int year,month,day;
    static Context mContext;
    ArrayList<CategoryName> CategoryNameList;
    String categoryName,address, lat, lng, place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
    }

    private void init() {
        Toolbarinit();
        mContext = RegistrationActivity.this;
        et_Name = (NeoGramMEditText) findViewById(R.id.et_Name);
        et_Email = (NeoGramMEditText) findViewById(R.id.et_Email);
        et_pwd = (NeoGramMEditText) findViewById(R.id.et_pwd);
        et_cnfm_pwd = (NeoGramMEditText) findViewById(R.id.et_cnfm_pwd);
        et_mobile = (NeoGramMEditText) findViewById(R.id.et_mobile);
        et_date = (NeoGramMEditText) findViewById(R.id.et_date);
        et_address = (NeoGramMEditText) findViewById(R.id.et_address);
        txt_gender = (Spinner) findViewById(R.id.txt_gender);
        spr_category = (Spinner) findViewById(R.id.txt_category);

        spr_category.setOnItemSelectedListener(this);
        findViewById(R.id.btn_create_Account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        findViewById(R.id.et_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                showDialog(1);
            }
        });

        et_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getplaces();
            }
        });

        CategoryNameList = new ArrayList<>();
        setGenderAdapter();
        getspinnerdata();
    }

    private void Toolbarinit() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);
        ((TypefaceTextview) v.findViewById(R.id.txt_title)).setText("Signup");
        getSupportActionBar().setCustomView(v);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Google", "Place: " + place.getName());
                et_address.setText(place.getName() + " , " + place.getAddress());

                address =  place.getAddress().toString();
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

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(this, R.style.MyDialogTheme, DateOfBirthDateListener, mYear, mMonth, mDay);

    }

    private DatePickerDialog.OnDateSetListener DateOfBirthDateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

            year = arg1;
            month = arg2 + 1;
            day = arg3;
            String strMonth = month + "";
            String strDay = day + "";
            if (month < 10)
                strMonth = "0" + strMonth;

            if (day < 10)
                strDay = "0" + strDay;
            String selectedDate = new StringBuilder().append(year).append("-")
                    .append(strMonth).append("-").append(strDay).toString();

            final Calendar c1 = Calendar.getInstance();
            int Year = c1.get(Calendar.YEAR);
            int Month = c1.get(Calendar.MONTH) + 1;
            int Day = c1.get(Calendar.DAY_OF_MONTH);
            String strMonth1 = Month + "";
            String strDay1 = Day + "";
            if (Month < 10)
                strMonth1 = "0" + strMonth1;

            if (Day < 10)
                strDay1 = "0" + strDay1;
            String currentDate = new StringBuilder().append(Year).append("-")
                    .append(strMonth1).append("-").append(strDay1).toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(selectedDate);

                Date today = Calendar.getInstance().getTime();
                if (selectedDate.equals(currentDate)) {
                    Toast.makeText(RegistrationActivity.this, "Please select valid date of birth", Toast.LENGTH_LONG).show();
//                    date_time =  new StringBuilder().append(strDay).append("-")
//                            .append(strMonth).append("-").append(year).toString();;
//                    timePicker();
                } else if (date.after(today)) {
                    Toast.makeText(RegistrationActivity.this, "Please select valid date of birth", Toast.LENGTH_LONG).show();
                } else if (date.before(today)) {
                    String date_time = new StringBuilder().append(year).append("-")
                            .append(strMonth).append("-").append(strDay).toString();
                    ;
                    et_date.setText(date_time);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private void setGenderAdapter() {
        String[] str = {"Select Gender", "Male", "Female"};
        ArrayList<String> arraytime = new ArrayList<String>(Arrays.asList(str));
        AttributeAdapter spOrderAdapter = new AttributeAdapter(RegistrationActivity.this, arraytime);
        txt_gender.setAdapter(spOrderAdapter);
    }

    private void register() {
        if (constant.isEmptyString(et_Name.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Please enter Name", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_Email.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Please enter Email", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_pwd.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Please enter Password", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_cnfm_pwd.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Please enter Confirm Password", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_mobile.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Please enter Mobile", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_date.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Please enter DOB", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_address.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Please enter Address", Toast.LENGTH_LONG).show();
        } else {
            if (!constant.isValidEmail(et_Email.getText().toString())) {
                Toast.makeText(RegistrationActivity.this, "Please enter valid Email", Toast.LENGTH_LONG).show();
            } else {
                if (et_mobile.getText().toString().trim().length() <= 9) {
                    Toast.makeText(RegistrationActivity.this, "Please enter valid Mobile", Toast.LENGTH_LONG).show();
                } else {
                    String gender = txt_gender.getSelectedItem().toString();
                    if (!gender.equals("Select Gender")) {
                        if(et_pwd.getText().toString().trim().equals(et_cnfm_pwd.getText().toString().trim())){
                            doregister();
                        }else{
                            Toast.makeText(RegistrationActivity.this, "Password and Confirm password should be equal", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(RegistrationActivity.this, "Please enter gender", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void doregister() {
        final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.show();
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/iam/v1/user/register";
        JSONObject parms = new JSONObject();
        try {
            parms.put("createdBy", "1");
            parms.put("updatedBy", "1");
            parms.put("createdAt", "2018-09-18T16:23:10Z");
            parms.put("updatedAt", "2018-09-18T16:23:13Z");
            parms.put("userName", et_Name.getText().toString().trim());
            parms.put("firstName", et_Name.getText().toString().trim());
            parms.put("lastName", et_Name.getText().toString().trim());
            parms.put("email", et_Email.getText().toString().trim());
            parms.put("userImage", "Guna");
            parms.put("phoneNumber", et_mobile.getText().toString().trim());
            parms.put("dateOfBirth", et_date.getText().toString()+" 21:52:30.0");
            parms.put("privacy","public");
            parms.put("gender", txt_gender.getSelectedItem().toString());
            parms.put("fcmId", "1");
            parms.put("registrationType", "Regular");
            parms.put("deviceId", "123");
            parms.put("deviceIp", "123.123");
            parms.put("lac_lat", lat);
            parms.put("lac_lang", lng);
            parms.put("locationName", et_address.getText().toString().trim());
            parms.put("otp", "12345");
            parms.put("accountVerified", "false");
            parms.put("accountLocked", "false");
            parms.put("credentialsExpired", "false");
            parms.put("noFailedAttempt", "0");
            parms.put("status", "A");
            parms.put("password", et_pwd.getText().toString().trim());
            parms.put("noFailedAttempt", "0");
            parms.put("categories", categoryName);
        } catch (Exception e) {

        }

        MyJsonObjectRequest myRequest = new MyJsonObjectRequest(
                Request.Method.POST, url,
                parms,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.e("onResponse", "#" + response);
                        registerresponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }) {
        };
        AppController.getInstance().addToRequestQueue(myRequest, constant.tag_json_obj, this);
    }

    private void registerresponse(JSONObject response){
        try {
            String status = response.getString("status");
            String id = response.getString("id");
            Log.e("id", "#" + id);
            Toast.makeText(RegistrationActivity.this, "Registration success", Toast.LENGTH_LONG).show();

            startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            finish();
        }catch (Exception e){
            Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void getspinnerdata() {
        final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.show();
        progressDialog.setMessage("Loading");
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
                Toast.makeText(RegistrationActivity.this, "Please try again", Toast.LENGTH_LONG).show();
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
        AppController.getInstance().addToRequestQueue(sr, "string_req", RegistrationActivity.this);
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
        CategoryListAdapter adapter = new CategoryListAdapter(RegistrationActivity.this, categoryNameList, 0);
        spr_category.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spr_category:
                CategoryName category = (CategoryName) adapterView.getItemAtPosition(i);
                String categoryId = category.getCategoryid();
                categoryName = category.getCategoryName();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
