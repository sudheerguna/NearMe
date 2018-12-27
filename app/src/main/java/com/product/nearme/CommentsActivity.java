package com.product.nearme;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.product.nearme.cview.NeoGramMEditText;
import com.product.nearme.cview.TypefaceTextview;
import com.product.nearme.utils.constant;
import com.product.nearme.volley.AppController;
import com.product.nearme.volley.MyJsonObjectRequest;

import org.json.JSONObject;

public class CommentsActivity extends AppCompatActivity {
    String username, location, category, AttributeName1, CategoryName1, AttributeName2, CategoryName2, AttributeName3, CategoryName3,PostId,userId;
    TypefaceTextview txt_username, txt_location, txt_category,txt_att1,txt_att2, txt_att3;
    RatingBar mRatingBar;
    NeoGramMEditText edi_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        init();
    }

    private void init() {
        Toolbarinit();

        txt_username = (TypefaceTextview) findViewById(R.id.txt_username);
        txt_location = (TypefaceTextview) findViewById(R.id.txt_location);
        txt_category = (TypefaceTextview) findViewById(R.id.txt_categorys);
        txt_att1 = (TypefaceTextview) findViewById(R.id.txt_att1);
        txt_att2 = (TypefaceTextview) findViewById(R.id.txt_att2);
        txt_att3 = (TypefaceTextview) findViewById(R.id.txt_att3);
        mRatingBar = (RatingBar) findViewById(R.id.ratingbar);
        edi_comment = (NeoGramMEditText) findViewById(R.id.edt_comment);

        category = getIntent().getStringExtra("CategoryName");
        username = getIntent().getStringExtra("UserName");
        location = getIntent().getStringExtra("Location");
        AttributeName1 = getIntent().getStringExtra("AttributeName1");
        CategoryName1 = getIntent().getStringExtra("CategoryName1");
        AttributeName2 = getIntent().getStringExtra("AttributeName2");
        CategoryName2 = getIntent().getStringExtra("CategoryName2");
        AttributeName3 = getIntent().getStringExtra("AttributeName3");
        CategoryName3 = getIntent().getStringExtra("CategoryName3");
        PostId = getIntent().getStringExtra("Id");
        userId = getIntent().getStringExtra("UserId");

        txt_username.setText(username);
        txt_location.setText(location);
        txt_category.setText(category);

        txt_att1.setText(CategoryName1 +" : "+AttributeName1);
        txt_att2.setText(CategoryName2 +" : "+AttributeName2);
        txt_att3.setText(CategoryName3 +" : "+AttributeName3);

        findViewById(R.id.txt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rating=String.valueOf(mRatingBar.getRating());
                Log.e("rating","#"+rating);
                PostCOmments(rating);
            }
        });

//        PostCOmments();
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
        ((TypefaceTextview) v.findViewById(R.id.txt_title)).setText("Comments");
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

    private void PostCOmments(String rating){
        final ProgressDialog progressDialog = new ProgressDialog(CommentsActivity.this);
        progressDialog.show();
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/iam/v1/posts/comments";
        JSONObject parms = new JSONObject();
        try {
            parms.put("commentId", "1234");
            parms.put("commenttext", edi_comment.getText().toString().trim());
            parms.put("postId", PostId);
            parms.put("postRating", rating);
            parms.put("postcommenterUserid", userId);
            parms.put("postCommenterProfileurl", "1");
        }catch (Exception e){

        }
        MyJsonObjectRequest myRequest = new MyJsonObjectRequest(
                Request.Method.POST, url,
                parms,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.e("onResponse", "#" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CommentsActivity.this, "Try Again", Toast.LENGTH_LONG).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }) {
        };
        AppController.getInstance().addToRequestQueue(myRequest, constant.tag_json_obj, this);

    }
}
