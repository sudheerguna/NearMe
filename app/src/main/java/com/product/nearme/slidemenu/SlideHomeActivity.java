package com.product.nearme.slidemenu;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.product.nearme.LoginActivity;
import com.product.nearme.R;
import com.product.nearme.utils.SharedPref;
import com.product.nearme.utils.constant;

public class SlideHomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{
    SharedPref mSharedPref;
    private FragmentDrawer drawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_home);

        init();
    }

    private void init(){
        init_toolbar();
        mSharedPref = new SharedPref(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        drawerFragment.txt_user.setText(mSharedPref.getString(constant.UserName));
        displayView(0);
    }

    @Override
    public void onDrawerItemSelected(View view, int position, NavigationDrawerAdapter adapter) {
        displayView(position);
    }

    private void init_toolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
            switch (position) {
                case 0:
                    fragment = new TabsFragment();
                    title = getString(R.string.home);
                    break;
                case 1:

                    break;
                case 2:
                    Logout();
                    break;
            }
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                // set the toolbar title
                getSupportActionBar().setTitle(title);

                this.getSupportActionBar().setDisplayShowCustomEnabled(true);
                this.getSupportActionBar().setDisplayShowTitleEnabled(false);
                LayoutInflater inflator = LayoutInflater.from(this);
                View v = inflator.inflate(R.layout.titleview, null);
                ((TextView) v.findViewById(R.id.txt_title)).setText(title);
                getSupportActionBar().setCustomView(v);
            }
    }

    private void Logout() {
        mSharedPref.clear();
        Intent intent = new Intent(SlideHomeActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
