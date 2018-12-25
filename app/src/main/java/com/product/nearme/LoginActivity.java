package com.product.nearme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.product.nearme.cview.NeoGramMEditText;
import com.product.nearme.utils.SharedPref;
import com.product.nearme.utils.constant;
import com.product.nearme.volley.AppController;
import com.product.nearme.volley.MyJsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    NeoGramMEditText Email, password;
    SharedPref mSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        mSharedPref = new SharedPref(LoginActivity.this);
        Email = (NeoGramMEditText) findViewById(R.id.et_email);
        password = (NeoGramMEditText) findViewById(R.id.etPassword);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dologin();
            }
        });

        findViewById(R.id.txt_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        findViewById(R.id.txt_forget_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void dologin() {
        if (constant.isEmptyString(Email.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
        } else if (constant.isEmptyString(password.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
        } else {
            if (!constant.isValidEmail(Email.getText().toString())) {
                Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_LONG).show();
            } else {
                callLogin(Email.getText().toString(), password.getText().toString());
            }
        }
    }

    private void callLogin(final String Email_st, final String password_st) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.show();
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/iam/v1/loginUser/login";
        JSONObject parms = new JSONObject();
        try {
            parms.put("email", Email_st);
            parms.put("password", password_st);
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
                        loginresponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_LONG).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }) {
        };
        AppController.getInstance().addToRequestQueue(myRequest, constant.tag_json_obj, this);
    }

    private void loginresponse(JSONObject response) {
        try {
            String statuscode = response.getString("statuscode");
            String statuscodeMessage = response.getString("statuscodeMessage");
            Log.e("statuscode", "#" + statuscode);
            if (statuscode.equals("0")) {
                Toast.makeText(LoginActivity.this, statuscodeMessage, Toast.LENGTH_LONG).show();
            } else {
                mSharedPref.putString(constant.Login_Success,"1");

                String id = response.getString("id");
                String userName = response.getString("userName");
                String firstName = response.getString("firstName");
                String lastName = response.getString("lastName");
                String email = response.getString("email");
                String userImage = response.getString("userImage");
                String phoneNumber = response.getString("phoneNumber");
                String dateOfBirth = response.getString("dateOfBirth");
                String privacy = response.getString("privacy");
                String categories = response.getString("categories");

                String gender = response.getString("gender");
                String fcmId = response.getString("fcmId");
                String registrationType = response.getString("registrationType");
                String deviceId = response.getString("deviceId");
                String locationName = response.getString("locationName");


                String otp = response.getString("otp");
                String accountVerified = response.getString("accountVerified");
                String accountLocked = response.getString("accountLocked");
                String credentialsExpired = response.getString("credentialsExpired");

                startActivity(new Intent(LoginActivity.this,Home_Activity.class));
                finish();
            }

        } catch (Exception e) {

        }

    }
}
