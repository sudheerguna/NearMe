package com.product.nearme;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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

public class ForgotPasswordActivity extends AppCompatActivity {
    NeoGramMEditText edt_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();
    }

    private void init(){
        Toolbarinit();
        edt_email = (NeoGramMEditText) findViewById(R.id.et_forget_email);
        findViewById(R.id.btn_forget_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(constant.isEmptyString(edt_email.getText().toString().trim())){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter Email", Toast.LENGTH_LONG).show();
                }else{
                    if (!constant.isValidEmail(edt_email.getText().toString())) {
                        Toast.makeText(ForgotPasswordActivity.this, "Please enter valid email", Toast.LENGTH_LONG).show();
                    } else {
                        do_forgetpwd(edt_email.getText().toString());
                    }
                }
            }
        });
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
        ((TypefaceTextview) v.findViewById(R.id.txt_title)).setText("Forgot Password");
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

    private void do_forgetpwd(String Email_st){
        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/iam/v1/loginUser/forgotpassword";
        JSONObject parms = new JSONObject();
        try {
            parms.put("email", Email_st);
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
                        forgotpwdresponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "#" + error.getMessage());
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }) {
        };
        AppController.getInstance().addToRequestQueue(myRequest, constant.tag_json_obj, this);
    }

    private void forgotpwdresponse(JSONObject response){

    }
}
