package com.product.nearme;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.product.nearme.utils.AndroidMultiPartEntity;
import com.product.nearme.utils.UtilitiesImage;
import com.product.nearme.utils.constant;
import com.product.nearme.volley.AppController;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import simplecropimage.CropImage;

public class EventsScreen extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    static NeoGramMEditText txt_starttime, txt_endtime, edt_mon_time, edt_mon_time2, edt_t_time1, edt_t_time2, edt_w_time1, edt_w_time2, edt_th_time1, edt_th_time2, edt_f_time1, edt_f_time2, edt_s_time1, edt_s_time2, edt_sn_time1, edt_sn_time2,
            et_name, et_desc, et_location, et_address, et_mobile, et_website, et_email;

    TypefaceTextview txt_mon, txt_tue, txt_wed, txt_thu, txt_fri, txt_sat, txt_sun;
    Boolean mon = true, tue = true, wed = true, thu = true, fri = true, sat = true, sun = true;
    ArrayList<CategoryName> CategoryNameList;
    Spinner spr_category;
    String categoryName, categoryId, address, lat, lng;
    RadioGroup radioGroup;
    private Integer PHOTO_PICK = 0x4, PHOTO_CLICK = 0x2, PHOTO_CROP = 0x3;
    private Uri mPhotoUri = null;
    private File imgFile, sdCardDirectory;
    String filepath = null, img_path;
    long totalSize = 0, image_pos = 0;
    RadioButton radioMale, radioFeMale;
    private ArrayList<String> files = new ArrayList<>();

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
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFeMale = (RadioButton) findViewById(R.id.radFemale);

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
        findViewById(R.id.img_1).setOnClickListener(this);
        findViewById(R.id.img_2).setOnClickListener(this);
        findViewById(R.id.img_3).setOnClickListener(this);

        findViewById(R.id.add_images).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);
        spr_category.setOnItemSelectedListener(this);
        findViewById(R.id.layout_images).setVisibility(View.GONE);

        Typeface font = Typeface.createFromAsset(getAssets(),
                "fonts/SF-Pro-Display-Regular.otf");
        radioMale.setTypeface(font);
        radioFeMale.setTypeface(font);

        findViewById(R.id.layout_timings).setVisibility(View.GONE);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
/*
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
*/

        delete_temps();
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
            case R.id.img_1:
                image_pos = 0;
                openimage();
                break;
            case R.id.img_2:
                image_pos = 1;
                openimage();
                break;
            case R.id.img_3:
                image_pos = 2;
                openimage();
                break;
            case R.id.add_images:
                Addimages();
                break;
        }
    }

    private void Addimages() {
        if (findViewById(R.id.layout_images).getVisibility() == View.VISIBLE) {
            findViewById(R.id.layout_images).setVisibility(View.GONE);
        } else {
            findViewById(R.id.layout_images).setVisibility(View.VISIBLE);
        }
    }

    private void openimage() {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (constant.haveCameraPermissions(EventsScreen.this)) {
                    if (constant.writePermissions(EventsScreen.this)) {
                        cameraFunctionality();
                    } else {
                        constant.requestwritePermission(EventsScreen.this);
                    }
                } else {
                    constant.requestCameraPermission(EventsScreen.this);
                }
            } else {
                cameraFunctionality();
            }

        } catch (Exception e) {

        }

    }

    private void cameraFunctionality() {

        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(
                EventsScreen.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mPhotoUri = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new ContentValues());

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                    startActivityForResult(intent, PHOTO_CLICK);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            PHOTO_PICK);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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
        if (resultCode == RESULT_OK && requestCode == PHOTO_PICK) {

                /*String filepath = UtilitiesImage
                        .getpath(data, BuyerProfileEditAactivity.this);

                startCropImage(filepath);*/


            String path = UtilitiesImage.getpath(data, EventsScreen.this);
            imgFile = new File(path);
            Bitmap bb11 = BitmapFactory.decodeFile(imgFile
                    .getAbsolutePath());

            sdCardDirectory = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "Sharent_temp");

            if (!sdCardDirectory.exists()) {
                if (!sdCardDirectory.mkdirs()) {
                    Log.d("MySnaps", "failed to create directory");
                }
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            String nw = "MFA_CROP_" + timeStamp + ".jpeg";

            File image = new File(sdCardDirectory, nw);

            boolean success = false;

            // Encode the file as a PNG image.
            FileOutputStream outStream;
            try {

                outStream = new FileOutputStream(image);
                bb11.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                /* 100 to keep full quality of the image */

                outStream.flush();
                outStream.close();
                success = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            img_path = String.valueOf(image);
            String filepath = String.valueOf(image); //UtilitiesImage.getpath(data, BuyerProfileEditAactivity.this);
            startCropImage(filepath);

        } else if (resultCode == RESULT_OK && requestCode == PHOTO_CLICK) {

            String conString = UtilitiesImage.convertImageUriToFile(mPhotoUri,
                    EventsScreen.this);
            startCropImage(conString);

        } else if (resultCode == RESULT_OK && requestCode == PHOTO_CROP) {

            String path = data.getStringExtra(CropImage.IMAGE_PATH);
            imgFile = new File(path);

            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
                        .getAbsolutePath());
                String filepath = path;
                files.add(filepath);
//               Bitmap conv_bm = getRoundedBitmap(myBitmap);
                if (image_pos == 0) {
                    ImageView img_profile = (ImageView) findViewById(R.id.img_1);
                    img_profile.setImageBitmap(myBitmap);
                } else if (image_pos == 1) {
                    ImageView img_profile = (ImageView) findViewById(R.id.img_2);
                    img_profile.setImageBitmap(myBitmap);
                } else if (image_pos == 2) {
                    ImageView img_profile = (ImageView) findViewById(R.id.img_3);
                    img_profile.setImageBitmap(myBitmap);
                }
            }
        }
    }

    private void startCropImage(String path) {

        Intent intent = new Intent(EventsScreen.this, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, path);
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_X, 2);
        intent.putExtra(CropImage.ASPECT_Y, 2);
        intent.putExtra(CropImage.CIRCLE_CROP, true);

        startActivityForResult(intent, PHOTO_CROP);
    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
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
            Toast.makeText(EventsScreen.this, "Enter name", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_desc.getText().toString())) {
            Toast.makeText(EventsScreen.this, "Enter description", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_address.getText().toString())) {
            Toast.makeText(EventsScreen.this, "Enter address", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_mobile.getText().toString())) {
            Toast.makeText(EventsScreen.this, "Enter mobile", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_email.getText().toString())) {
            Toast.makeText(EventsScreen.this, "Enter email", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(et_website.getText().toString())) {
            Toast.makeText(EventsScreen.this, "Enter website", Toast.LENGTH_LONG).show();
        } else {
            if (et_mobile.getText().toString().length() <= 9) {
                Toast.makeText(EventsScreen.this, "Mobile Number should be 10 digits", Toast.LENGTH_LONG).show();
            } else if (!constant.isValidEmail(et_email.getText().toString())) {
                Toast.makeText(EventsScreen.this, "Enter valid email", Toast.LENGTH_LONG).show();
            }else {
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
                    jsonObj.put("loc_lat", String.valueOf(lat));
                    jsonObj.put("loc_long", String.valueOf(lng));
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

//                uploaddata(jsonObj);

                    new uplaodmultiprtdata(jsonObj).execute();
                } catch (Exception e) {

                }
            }
        }
    }

    private class uplaodmultiprtdata extends AsyncTask<Void, Integer, String> {
        ProgressDialog mProgressDialog;
        JSONObject jsonObj;

        public uplaodmultiprtdata(JSONObject jsonObj) {
            this.jsonObj = jsonObj;
        }

        @Override
        protected void onPreExecute() {
            //progressBar.setProgress(0);

            try {
                mProgressDialog = new ProgressDialog(EventsScreen.this);
                mProgressDialog.setTitle("Uploading");
                mProgressDialog.setMessage("Please Wait!");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // Show ProgressBar
                mProgressDialog.setCancelable(false);
                //  mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

            } catch (Exception e) {
                // TODO: handle exception
            }
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {

            String responseString = null;

            String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/pbc/v1/pbcast/createads";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
//            httppost.addHeader("Content-Type", "application/octet-stream");
//            httppost.addHeader("Content-Type", "multipart/form-data");


            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                try {
                    if (files.size() >= 1) {
                        File file1 = new File(files.get(0));
                        FileInputStream fileInputStream = new FileInputStream(file1);
                        entity.addPart("Imagefile1", new InputStreamBody(fileInputStream, "image/jpeg", "file_name.jpg"));

                        File file2 = new File(files.get(1));
                        FileInputStream fileInputStream2 = new FileInputStream(file2);
                        entity.addPart("Imagefile2", new InputStreamBody(fileInputStream2, "image/jpeg", "file_name.jpg"));
//
//                        File file3 = new File(files.get(2));
//                        FileInputStream fileInputStream3 = new FileInputStream(file3);
//                        entity.addPart("Imagefile3", new InputStreamBody(fileInputStream3, "image/jpeg", "file_name.jpg"));
                    }

                    /*if (filepath != null) {
                        File sourceFile = new File(filepath);
//                        entity.addPart("profile_image", new FileBody(sourceFile));
                        Log.e("sourceFile","#"+sourceFile);

                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        entity.addPart("Imagefile1", new InputStreamBody(fileInputStream, "image/jpeg", "file_name.jpg"));
                    }*/


                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

                entity.addPart("createbeanmodel", new StringBody(jsonObj.toString()));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            Log.e("responseString", responseString);
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            // showing the server response in an alert dialog
            //showAlert(result);
            files.clear();

            super.onPostExecute(result);

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            postresponse(result);

        }
    }

    private void postresponse(String result) {
        delete_temps();
        Log.e("result", result);
        try {
            JSONObject jsonObject = new JSONObject(result);
//            Log.e("jsonArray","#"+jsonObject.toString());

            for (int i = 0; i < jsonObject.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String categoryid = jsonObject.getString("categoryId");
                String category_name = jsonObject.getString("categoryName");

                Log.e("category_name>>>>>", category_name);
                if (!constant.isEmptyString(id)) {
                    Toast.makeText(EventsScreen.this, "Success", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Log.e("error>>>>>", e.getMessage());
            Toast.makeText(EventsScreen.this, "Error", Toast.LENGTH_LONG).show();
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

    public void delete_temps() {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/Pictures/Sharent_temp");
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        } catch (Exception e) {

        }

    }
}
