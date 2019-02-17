package com.example.udaysaikumar.clgattendance.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;

import com.example.udaysaikumar.clgattendance.Adapters.BottomPagerAdapter;
import com.example.udaysaikumar.clgattendance.Interfaces.ConnectionInterface;
import com.example.udaysaikumar.clgattendance.Interfaces.ImageInterface;
import com.example.udaysaikumar.clgattendance.CustomViewPage.PagerTransformer;
import com.example.udaysaikumar.clgattendance.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
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
import android.widget.Toolbar;

import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Attendance;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Feedback;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Home;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Marks;

import java.util.Objects;

public class BottomBarActivity extends AppCompatActivity implements ConnectionInterface,ImageInterface {
    Fragment_Home fragment_home;
   Fragment_Attendance fragment_attendance;
   Fragment_Marks fragment_marks;
   Fragment_Feedback fragment_feedback;
BottomNavigationView bottomNavigationView;
RelativeLayout relativeLayout;
CoordinatorLayout coordinatorLayout;
FrameLayout frameLayout;
Toolbar toolbar;
int i;
//FrameLayout myLayout;
    ViewPager viewPager;
    private String TAG="BottomBarActivity_Log";
Fragment fragment=null;
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
//        ImageView imageView = new ImageView(getSupportActionBar().getThemedContext());
//        imageView.setScaleType(ImageView.ScaleType.CENTER);
//        imageView.setImageResource(R.drawable.logo);
//        getSupportActionBar().setCustomView(imageView);

        coordinatorLayout=findViewById(R.id.mycoordinate);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        relativeLayout=findViewById(R.id.myrelative);
       // frameLayout=findViewById(R.id.frame);
viewPager=findViewById(R.id.viewPager);
viewPager.setOffscreenPageLimit(3);
//myLayout=findViewById(R.id.myLayout);
viewPager.setPageTransformer(false,new PagerTransformer());
        final FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
      fragment_attendance=new Fragment_Attendance();
      fragment_marks=new Fragment_Marks();
      fragment_home=new Fragment_Home();
      fragment_feedback=new Fragment_Feedback();
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
       // fragmentTransaction.replace(R.id.myLayout,fragment_home);
        //fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.commit();
        //loadFragment(fragment_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        CharSequence s1;
        switch (menuItem.getItemId())
        {
            case R.id.menu_home:
                //Log.d("fragmentcheck","home");
               //fragment=fragment_home;
                viewPager.setCurrentItem(0);
                break;
                //return  true;

            case R.id.menu_attendance:
                Log.d("fragmentcheck","home1");
               // fragment=fragment_attendance;
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
               // fragment=fragment_marks;
                viewPager.setCurrentItem(2);
                break;
                //return true;
            case R.id.feedback:
               // Log.d("fragmentcheck","home3");
              //  fragmentTransaction.replace(R.id.myLayout,new Fragment_Feedback());
               // fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.commit();
               // fragment=fragment_feedback;
                viewPager.setCurrentItem(3);
                break;



               // return  true;


        }
      //  return  loadFragment(fragment);
        return true;
    }
});
checkNet();
    }
    @SuppressLint("ClickableViewAccessibility")
    public void checkNet(){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


            if (networkInfo != null && networkInfo.isConnected()) {
                bottomNavigationView.setSelectedItemId(R.id.menu_home);
                setUpPager(viewPager);
            } else {
                //   Toast.makeText(getApplicationContext(),"what this fuck",Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(relativeLayout, "No internt connection", Snackbar.LENGTH_INDEFINITE).setAction("retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkNet();
                    }
                }).setBehavior(new BaseTransientBottomBar.Behavior() {
                    @Override
                    public boolean canSwipeDismissView(View view) {
                        return false;
                    }

                });

                snackbar.show();


            }
        }
            catch (Exception e){
                Log.d("bottombarActive",e.getMessage());
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

    @Override
    public void reload() {
        checkNet();
    }

    @Override
    public void setImage(Bitmap bitmap) {
        ActionBar actionBar = getSupportActionBar();
try {
//    actionBar.setDisplayShowCustomEnabled(true);
//    LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    View v = inflator.inflate(R.layout.actionbar_customview, null);
//ImageView imageView=v.findViewById(R.id.myCustomActionBarImage);
//imageView.setImageBitmap(bitmap);
//    actionBar.setCustomView(v);
    //    actionBar.setDisplayOptions(actionBar.getDisplayOptions()
//            | ActionBar.DISPLAY_SHOW_CUSTOM);
//    ImageView imageView = new ImageView(actionBar.getThemedContext());
//    //imageView.set
//    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//    imageView.setImageBitmap(bitmap);
//    ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
//            ActionBar.LayoutParams.WRAP_CONTENT,
//            ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
//            | Gravity.CENTER_VERTICAL);
//    imageView.setLayoutParams(layoutParams);
    //    imageView.getLayoutParams().width=actionBar.getHeight();
    //  imageView.getLayoutParams().height=actionBar.getHeight();
   // actionBar.setCustomView(imageView);
}catch (Exception e)
{

}
    }
    /*private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
          fragmentTransaction
                    .replace(R.id.myLayout, fragment).addToBackStack(null)
                    .commit();

            return true;
        }
        return false;
    }*/

}
