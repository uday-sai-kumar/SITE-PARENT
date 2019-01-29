package com.example.udaysaikumar.clgattendance.Fragments;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.udaysaikumar.clgattendance.BottomBarActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.example.udaysaikumar.clgattendance.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Marks extends Fragment {
ViewPager viewPager;
TabLayout tabLayout;
ProgressBar progressBar;
FrameLayout frameLayout;
RelativeLayout relativeLayout;
String s;
ImageView emoji;
TextView bad,satisfacotry,excellent;
    String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
    RetroGet retroGet;
    JSONArray j;
    JSONObject job;
    String st;
    List<String> list=new ArrayList<>();
    List<String> listSecond=new ArrayList<>();
    Map<String,JSONObject> map=new LinkedHashMap<>();
CircleDisplay cd;
SeekBar seekBar;
    TextView myText;
    int maxX;
    int imageId;
   // ImageView myImage;
    View v;
    ViewPagerAdapter viewPagerAdapter;
    public Fragment_Marks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v=inflater.inflate(R.layout.fragment_fragment__marks, container, false);
       // progressBar=v.findViewById(R.id.marksprogress);
       // relativeLayout=v.findViewById(R.id.relativemarks);
       // bad=v.findViewById(R.id.bad);
        seekBar=v.findViewById(R.id.seekBar);
        myText=v.findViewById(R.id.myView);
       /// satisfacotry=v.findViewById(R.id.satisfactory);
        //excellent=v.findViewById(R.id.excellent);
        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        final String UNAME=sharedPreferences.getString("username","");
       // String PASS=sharedPreferences.getString("password","");
        String MARKS=sharedPreferences.getString("marks","");
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        Point point=new Point();
        try {

            getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        }
        catch (Exception e){}
        Log.d("wholepositions",point.toString());
        maxX=point.x;

        String q="{\"regno\":{$eq:\""+ UNAME+"\"}}";
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataCall = retroGet.getMarksData(MARKS,API_KEY,q);
        dataCall.enqueue(new Callback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.body()!=null)
                {
                    String json=response.body();
                    st=json;

                    try {
                        j = new JSONArray(json);
                        job = j.getJSONObject(0);
                        Iterator<String> it1=job.keys();
                        Iterator<String> it=job.keys();
                        while (it.hasNext()){
                            s=it.next();
                        }

                        JSONObject jj=job.getJSONObject(s);
                        JSONObject jj1=jj.getJSONObject("Final");
                        Iterator<String> itt1=jj1.keys();
                        Iterator<String> itt2=jj1.keys();
                        String ss=null;
                        String ss1=null;
                        boolean b=false;
                        while (itt1.hasNext()){
                            ss=itt1.next();
                            if(ss.contains("CGPA")){
                                b=true;
                                break;
                            }

                        }
                        if(!b){
                            while (itt2.hasNext()){
                                ss1=itt2.next();
                                if(ss1.contains("SGPA")){
                                    ss=ss1;
                                    break;
                                }

                            }
                        }
                        String DE="%";
                        //  SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
                        // final String UNAME=sharedPreferences.getString("username","");
                        if (Integer.parseInt(UNAME.subSequence(0, 2).toString()) > 15) {
                            //cd.showValue(Float.parseFloat(jj1.get(ss).toString()), 100f, false);
                            Float f=Float.parseFloat(jj1.get(ss).toString());
                            // Float floatval=6.4f;
                            myText.setText(f.toString());
                            seekBar.setProgress(Math.round(f));
                            final ViewTreeObserver viewTreeObserver=seekBar.getViewTreeObserver();
                            if(viewTreeObserver.isAlive())
                            {
                                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        Log.d("wholepositions1",String.valueOf(maxX));
                                        Log.d("wholepositions2",String.valueOf(seekBar.getWidth()));
                                        Log.d("wholepositions3",String.valueOf(seekBar.getThumbOffset()));
                                        int val = (seekBar.getProgress() * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                                        Log.d("wholepositions3",String.valueOf(seekBar.getProgress()));
                                        Log.d("wholepositions4",String.valueOf((seekBar.getWidth() - 2 * seekBar.getThumbOffset())));

                                        int textViewX = val - (myText.getWidth() / 2);
                                        Log.d("wholepositions5",String.valueOf(val));
                                        Log.d("wholepositions6",String.valueOf(textViewX));

                                        int finalX = myText.getWidth() + textViewX > maxX ? (maxX - myText.getWidth()) : textViewX /*your margin*/;
                                        Log.d("wholepositions7",String.valueOf(finalX));
                                        myText.setX(finalX < 0 ? 0/*your margin*/ : finalX);
                                        myText.setTextSize(25);
                                        Log.d("wholepositions","x is"+String.valueOf(v.getX())+"y is "+String.valueOf(v.getX()));
                                        seekBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                    }
                                });
                            }
                            if(f>=7){
                                imageId=R.drawable.ic_cool;
                                // excellent.setText(f.toString());
                                // excellent.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_excellent));
                            }
                            else if( f>6){
                                imageId=R.drawable.ic_sad;
                                // satisfacotry.setText(f.toString());
                                //  satisfacotry.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_satisfactory));

                            }
                            else {
                                imageId=R.drawable.ic_crying;
                                // bad.setText(f.toString());
                                // bad.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_bad));


                            }
                        }
                        else{
                            Float f=Float.parseFloat(jj1.get("%f").toString());
                            myText.setText(f.toString());
                            seekBar.setProgress(Math.round(f));
                            final ViewTreeObserver viewTreeObserver=seekBar.getViewTreeObserver();
                            if(viewTreeObserver.isAlive())
                            {
                                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        Log.d("wholepositions1",String.valueOf(maxX));
                                        Log.d("wholepositions2",String.valueOf(seekBar.getWidth()));
                                        Log.d("wholepositions3",String.valueOf(seekBar.getThumbOffset()));
                                        int val = (seekBar.getProgress() * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                                        Log.d("wholepositions3",String.valueOf(seekBar.getProgress()));
                                        Log.d("wholepositions4",String.valueOf((seekBar.getWidth() - 2 * seekBar.getThumbOffset())));

                                        int textViewX = val - (myText.getWidth() / 2);
                                        Log.d("wholepositions5",String.valueOf(val));
                                        Log.d("wholepositions6",String.valueOf(textViewX));

                                        int finalX = myText.getWidth() + textViewX > maxX ? (maxX - myText.getWidth()) : textViewX /*your margin*/;
                                        Log.d("wholepositions7",String.valueOf(finalX));
                                        myText.setX(finalX < 0 ? 0/*your margin*/ : finalX);
                                        myText.setTextSize(25);
                                        Log.d("wholepositions","x is"+String.valueOf(v.getX())+"y is "+String.valueOf(v.getX()));
                                        seekBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                    }
                                });
                            }
                            if(f>=75){
                                imageId=R.drawable.ic_cool;
                                //  excellent.setText(f.toString());
                                // excellent.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_excellent));

                            }else if(f>65){
                                imageId=R.drawable.ic_sad;
                                /// satisfacotry.setText(f.toString());
                                //  satisfacotry.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_satisfactory));

                            }
                            else {
                                imageId=R.drawable.ic_crying;
                                //  bad.setText(f.toString());
                                // bad.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_bad));

                            }

                        }
                        seekBar.setThumb(getResources().getDrawable(imageId));
                        //  seekBar.setThumb(drawForSeekBar(v.getContext(),R.drawable.thumb_image_ic_cool));
                        while (it1.hasNext()) {
                            list.add(it1.next());

                        }
                        for (String s : list) {
                            try {
                                map.put(s, job.getJSONObject(s));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        map.remove("_id");
                        for (Map.Entry<String, JSONObject> maps : map.entrySet()) {
                            listSecond.add(maps.getValue().toString());
                        }

                        viewPager= v.findViewById(R.id.viewPager);
                        tabLayout=v.findViewById(R.id.tabs);
                        viewPager.setOffscreenPageLimit(8);
                        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager(),map,listSecond);
                        viewPager.setAdapter(adapter);
                        // viewPager.arrowScroll(ViewPager.FOCUS_RIGHT);
                        tabLayout.setupWithViewPager(viewPager);
                       // progressBar.setVisibility(View.INVISIBLE);
                       // relativeLayout.setVisibility(View.VISIBLE);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else{
                    //progressBar.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
               // progressBar.setVisibility(View.INVISIBLE);

                //Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();
            }
        });



        return v;

    }

}
