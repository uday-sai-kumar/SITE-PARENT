package com.example.udaysaikumar.clgattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Attendance;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Feedback;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Home;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Marks;
import com.example.udaysaikumar.clgattendance.Login.MainActivity;

import java.util.Objects;

public class BottomBarActivity extends AppCompatActivity {
    Fragment_Home fragment_home;
   Fragment_Attendance fragment_attendance;
   Fragment_Marks fragment_marks;
BottomNavigationView bottomNavigationView;
RelativeLayout relativeLayout;
CoordinatorLayout coordinatorLayout;
FrameLayout frameLayout;
int i;
//RelativeLayout myLayout;
    ViewPager viewPager;
Fragment frag=null;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
       switch (item.getItemId())
       {
           case R.id.logat:{
               SharedPreferences sharedPreferences=getSharedPreferences("MyLogin",MODE_PRIVATE);
               sharedPreferences.edit().remove("logged").apply();
               sharedPreferences.edit().remove("username").apply();
               sharedPreferences.edit().remove("password").apply();
               sharedPreferences.edit().remove("phone").apply();
               sharedPreferences.edit().remove("otp").apply();
               sharedPreferences.edit().remove("marks").apply();
               sharedPreferences.edit().remove("profile").apply();
               sharedPreferences.edit().remove("attendance").apply();
               Intent i=new Intent(getApplicationContext(),MainActivity.class);
               startActivity(i);
               finish();
           }
       }
        return  super.onOptionsItemSelected(item);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        coordinatorLayout=findViewById(R.id.mycoordinate);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
      //  relativeLayout=findViewById(R.id.myrelative);
       // frameLayout=findViewById(R.id.frame);
viewPager=findViewById(R.id.viewPager);
viewPager.setOffscreenPageLimit(3);
//myLayout=findViewById(R.id.myLayout);
//viewPager.setPageTransformer(false,new PagerTransformer());
        final FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
      fragment_attendance=new Fragment_Attendance();
      fragment_marks=new Fragment_Marks();
      fragment_home=new Fragment_Home();
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
       // fragmentTransaction.replace(R.id.myLayout,fragment_home);
        //fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        CharSequence s1;
        switch (menuItem.getItemId())
        {
            case R.id.menu_home:
                //Log.d("fragmentcheck","home");
               // fragmentTransaction.replace(R.id.myLayout,fragment_home);
                viewPager.setCurrentItem(0);
                break;
                //return  true;

            case R.id.menu_attendance:
                Log.d("fragmentcheck","home1");
             //   fragmentTransaction.replace(R.id.myLayout,fragment_attendance);
               // fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.commit();

                viewPager.setCurrentItem(1);
                break;
               // return  true;

            case R.id.menu_marks:
                //Log.d("fragmentcheck","home2");
                //fragmentTransaction.replace(R.id.myLayout,fragment_marks);
                //fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.commit();
                 viewPager.setCurrentItem(2);
                break;
                //return true;
            case R.id.feedback:
                Log.d("fragmentcheck","home3");
              //  fragmentTransaction.replace(R.id.myLayout,new Fragment_Feedback());
               // fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.commit();
                 viewPager.setCurrentItem(3);
                break;



               // return  true;


        }
        return  true;
    }
});
checkNet();
    }
    public void checkNet(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();


            if (networkInfo!=null && networkInfo.isConnected()) {
                bottomNavigationView.setSelectedItemId(R.id.menu_home);
                setUpPager(viewPager);
            } else {
                Snackbar snackbar = Snackbar.make(relativeLayout, "No internt connection", Snackbar.LENGTH_INDEFINITE).setAction("retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkNet();
                    }
                });
                snackbar.show();


            }
        
    }
    private  void setUpPager(ViewPager pager){

        BottomPagerAdapter pagerAdapter = new BottomPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFrag(new Fragment_Home());
        pagerAdapter.addFrag(new Fragment_Attendance());
        pagerAdapter.addFrag(new Fragment_Marks());
        pagerAdapter.addFrag(new Fragment_Feedback());
        pager.setAdapter(pagerAdapter);

    }

}
