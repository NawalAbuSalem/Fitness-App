package com.nns.youcan.Views.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nns.youcan.Models.User;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.GoogleFitAPIViewModel;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;
import com.nns.youcan.Views.LoginActivity;
import com.nns.youcan.Workers.WaterAlarmWorker;
import com.nns.youcan.Views.home.DashboardFragments.DashboardFragment;
import com.nns.youcan.Views.home.HomeFragments.HomeFragment;
import com.nns.youcan.Views.home.MyGoalFragment.MyGoalFragment;
import com.nns.youcan.Views.profile.AccountProfileActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private MyGoalFragment myGoalFragment;
    private GoogleFitAPIViewModel fitAPIViewModel;
    private FragmentManager fragmentManager;
   private Intent profileIntent;
   private UsersFireStoreViewModel fireStoreViewModel;
    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fitAPIViewModel=new GoogleFitAPIViewModel(this);
        fireStoreViewModel=new UsersFireStoreViewModel();
        if (!(fitAPIViewModel.isRegistered())){
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GoogleFitAPIViewModel.GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this));
        }
        profileIntent=new Intent(this,AccountProfileActivity.class);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        //update header date
        updateHeaderData();
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        myGoalFragment=new MyGoalFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_cotainer, homeFragment).commit();
    }

    private void updateHeaderData() {
        View headerView = navigationView.getHeaderView(0);
        TextView header_name = headerView.findViewById(R.id.name_txt);
        ImageView header_close = headerView.findViewById(R.id.close_img);
        CircularImageView header_profile_img = headerView.findViewById(R.id.profile_img);
        header_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close drawer
                onBackPressed();
            }
        });
        MutableLiveData<User> liveData=fireStoreViewModel.getCurrentUserInformation();
        liveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user.getUserImage()!=null&&!user.getUserImage().equals("")){
                    Picasso.get().load(user.getUserImage()).into(header_profile_img);
                }
                if (user.getUserName()!=null&&!user.getUserName().equals("")){
                    header_name.setText(user.getUserName());
                }
            }
        });

        headerView.findViewById(R.id.view_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(profileIntent);
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressLint("RestrictedApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.home_cotainer, homeFragment).commit();

        } else if (id == R.id.nav_dashboard) {
            fragmentManager.beginTransaction().replace(R.id.home_cotainer, dashboardFragment).commit();

        }  else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_nutrition) {

        } else if (id == R.id.nav_mygoals) {
            fragmentManager.beginTransaction().replace(R.id.home_cotainer, myGoalFragment).commit();

        }  else if (id == R.id.nav_logout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void showDrawer(View view) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(Gravity.START);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GoogleFitAPIViewModel.GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                fitAPIViewModel.accessGoogleFit();
            }
        }

    }
}
