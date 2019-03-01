package com.example.udaysaikumar.clgattendance.Fragments;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.example.udaysaikumar.clgattendance.Adapters.ViewPagerAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
private ViewPager viewPager;
private TabLayout tabLayout;
private String s;
   private String API_KEY;
   private RetroGet retroGet;
    private JSONArray j;
  private   JSONObject job;
 private    List<String> list=new ArrayList<>();
    private List<String> listSecond=new ArrayList<>();
  private   Map<String,JSONObject> map=new LinkedHashMap<>();
private SeekBar seekBar,seekBarPercentage;
 private    TextView myText,showPercentage;
 private    int maxX;
   private int imageId;
   private ProgressBar progressBar;
   private LinearLayout linearLayout;
   private View v;
   private String MARKS="MARKS";
   private String TAG="Fragment_Marks_Log";
   private String finalPercentage;
   private  DecimalFormat df;
   private String  UNAME;
   private Map<String,String> myPercentage=new LinkedHashMap<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v=inflater.inflate(R.layout.fragment_fragment__marks, container, false);
        seekBar=v.findViewById(R.id.seekBar);
        myText=v.findViewById(R.id.myView);
        API_KEY=getResources().getString(R.string.APIKEY);
        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
         UNAME=sharedPreferences.getString("username","");
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        progressBar=v.findViewById(R.id.marksProgress);
        linearLayout=v.findViewById(R.id.marksLayout);
        showPercentage=v.findViewById(R.id.showPercentage);
        seekBarPercentage=v.findViewById(R.id.seekBarPercentage);
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
        catch (Exception e){

        }
        //    Log.d("wholepositions",point.toString());
        maxX=point.x;
        showPercentage();
        return v;

    }
    public void showIndividual()
    {
        final ViewTreeObserver viewTreeObserver = seekBarPercentage.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Log.d("wholepositions1", String.valueOf(maxX));
                    // Log.d("wholepositions2", String.valueOf(seekBar.getWidth()));
                    // Log.d("wholepositions3", String.valueOf(seekBar.getThumbOffset()));
                    int val = (seekBarPercentage.getProgress() * (seekBarPercentage.getWidth() - 2 * seekBarPercentage.getThumbOffset())) / seekBar.getMax();
                    //  Log.d("wholepositions3", String.valueOf(seekBar.getProgress()));
                    //  Log.d("wholepositions4", String.valueOf((seekBar.getWidth() - 2 * seekBar.getThumbOffset())));

                    int textViewX = val - (showPercentage.getWidth() / 2);
                    //   Log.d("wholepositions5", String.valueOf(val));
                    // Log.d("wholepositions6", String.valueOf(textViewX));

                    int finalX = showPercentage.getWidth() + textViewX > maxX ? (maxX - showPercentage.getWidth()) : textViewX /*your margin*/;
                    // Log.d("wholepositions7", String.valueOf(finalX));
                    showPercentage.setX(finalX < 0 ? 0/*your margin*/ : finalX);
                    showPercentage.setTextSize(25);
                    // Log.d("wholepositions", "x is" + String.valueOf(v.getX()) + "y is " + String.valueOf(v.getX()));
                    seekBarPercentage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }
    public void showPercentage()
    {
        showProgress();
       // tabLayout.removeAllViewsInLayout();
        String q="{\"regno\":{$eq:\""+ UNAME+"\"}}";
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataCall = retroGet.getMarksData(MARKS,API_KEY,q);
        dataCall.enqueue(new Callback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.body()!=null)
                {
                    //  Log.d(TAG,response.body());
                    String json=response.body();

                    try {
                        j = new JSONArray(json);
                        job = j.getJSONObject(0);
                        Iterator<String> it1 = job.keys();
                        Iterator<String> it = job.keys();
                        while (it.hasNext()) {
                            s = it.next();
                            try{
                               // Log.d(TAG,s);
                              //  Log.d(TAG,job.getJSONObject(s).toString());
                            myPercentage.put(s, job.getJSONObject(s).getJSONObject("final").getString("%"));
                        }catch (JSONException e)
                            {

                            }
                        }

                        JSONObject jj = job.getJSONObject(s);
                        JSONObject jj1 = jj.getJSONObject("final");
                        Iterator<String> itt1 = jj1.keys();
                        Iterator<String> itt2 = jj1.keys();
                        String ss = null;
                        String ss1 = null;
                        boolean b = false;

                        while (itt1.hasNext()) {
                            ss = itt1.next();
                           // myPercentage.put(ss,jj1.get)
                            try {
                                //  Log.d(TAG, jj1.getString(ss));
                                if (ss.contains("% up to")) {
                                    finalPercentage = jj1.getString(ss);
                                    b = true;
                                    break;

                                } else {
                                    if (ss.contains("CGPA up to") || ss.contains("CGPA")) {
                                        seekBar.setMax(10);
                                        finalPercentage = jj1.getString(ss);
                                        // Log.d(TAG+"good", finalPercentage);
                                    }

                                    if (ss.contains("CGPA")) {
                                        // Log.d(TAG+"good","executed");
                                        b = true;
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        if (!b) {
                            while (itt2.hasNext()) {
                                ss1 = itt2.next();
                                try {
                                    if (ss.contains("SGPA")) {
                                        seekBar.setMax(10);
                                        finalPercentage = jj1.getString(ss);
                                    }
                                    if (ss1.contains("SGPA")) {
                                        ss = ss1;
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                        String DE = "%";

                        try {
                            // Log.d(TAG+"good",finalPercentage);
                            finalPercentage = df.format(Double.valueOf(finalPercentage));
                            //  Log.d(TAG+"good",finalPercentage);
                            Double ff=Double.parseDouble(finalPercentage);
                            // Log.d(TAG+"good",finalPercentage);
                            // Log.d(TAG,finalPercentage);
                            //  SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
                            // final String UNAME=sharedPreferences.getString("username","");
                            // Log.d(TAG,"reached 123");
                            // Log.d(TAG, String.valueOf(ff));
                            // Log.d(TAG,"reached 124");
                            if (ff <= 10) {
                                //cd.showValue(Float.parseFloat(jj1.get(ss).toString()), 100f, false);
                                //Double f = ff;
                                // Float floatval=6.4f;
                                myText.setText(ff.toString());
                                seekBar.setProgress(ff.intValue());
                                final ViewTreeObserver viewTreeObserver = seekBar.getViewTreeObserver();
                                if (viewTreeObserver.isAlive()) {
                                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                        @Override
                                        public void onGlobalLayout() {
                                            // Log.d("wholepositions1", String.valueOf(maxX));
                                            // Log.d("wholepositions2", String.valueOf(seekBar.getWidth()));
                                            // Log.d("wholepositions3", String.valueOf(seekBar.getThumbOffset()));
                                            int val = (seekBar.getProgress() * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                                            //  Log.d("wholepositions3", String.valueOf(seekBar.getProgress()));
                                            //  Log.d("wholepositions4", String.valueOf((seekBar.getWidth() - 2 * seekBar.getThumbOffset())));

                                            int textViewX = val - (myText.getWidth() / 2);
                                            //   Log.d("wholepositions5", String.valueOf(val));
                                            // Log.d("wholepositions6", String.valueOf(textViewX));

                                            int finalX = myText.getWidth() + textViewX > maxX ? (maxX - myText.getWidth()) : textViewX /*your margin*/;
                                            // Log.d("wholepositions7", String.valueOf(finalX));
                                            myText.setX(finalX < 0 ? 0/*your margin*/ : finalX);
                                            myText.setTextSize(25);
                                            // Log.d("wholepositions", "x is" + String.valueOf(v.getX()) + "y is " + String.valueOf(v.getX()));
                                            seekBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                        }
                                    });
                                }
                                if (ff >= 7) {
                                    imageId = R.drawable.ic_cool;
                                    // excellent.setText(f.toString());
                                    // excellent.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_excellent));
                                } else if (ff >= 6) {
                                    imageId = R.drawable.ic_sad;
                                    // satisfacotry.setText(f.toString());
                                    //  satisfacotry.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_satisfactory));

                                } else {
                                    imageId = R.drawable.ic_crying;
                                    // bad.setText(f.toString());
                                    // bad.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_bad));


                                }
                            } else {
                                //   Log.d(TAG,"reached 0");
                                // Log.d(TAG,String.valueOf(ff));
                                myText.setText(ff.toString());
                                seekBar.setProgress(ff.intValue());
                                // Log.d(TAG,"reached1");
                                //Log.d(TAG,String.valueOf(Math.round(ff)));
                                // Log.d(TAG,"reached2");

                                final ViewTreeObserver viewTreeObserver = seekBar.getViewTreeObserver();
                                // Log.d(TAG,"reached3");

                                if (viewTreeObserver.isAlive()) {
                                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                        @Override
                                        public void onGlobalLayout() {
//                                            Log.d("wholepositions1", String.valueOf(maxX));
//                                            Log.d("wholepositions2", String.valueOf(seekBar.getWidth()));
//                                            Log.d("wholepositions3", String.valueOf(seekBar.getThumbOffset()));
                                            int val = (seekBar.getProgress() * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
//                                            Log.d("wholepositions3", String.valueOf(seekBar.getProgress()));
//                                            Log.d("wholepositions4", String.valueOf((seekBar.getWidth() - 2 * seekBar.getThumbOffset())));

                                            int textViewX = val - (myText.getWidth() / 2);
//                                            Log.d("wholepositions5", String.valueOf(val));
//                                            Log.d("wholepositions6", String.valueOf(textViewX));

                                            int finalX = myText.getWidth() + textViewX > maxX ? (maxX - myText.getWidth()) : textViewX /*your margin*/;
                                            //  Log.d("wholepositions7", String.valueOf(finalX));
                                            myText.setX(finalX < 0 ? 0/*your margin*/ : finalX);
                                            myText.setTextSize(25);
                                            // Log.d("wholepositions", "x is" + String.valueOf(v.getX()) + "y is " + String.valueOf(v.getX()));
                                            seekBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                        }
                                    });
                                }
                                //  Log.d(TAG,"reached4");

                                if (ff.intValue() >= 75) {
                                    imageId = R.drawable.ic_cool;
                                    //  excellent.setText(f.toString());
                                    // excellent.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_excellent));

                                } else if (ff.intValue() > 65) {
                                    imageId = R.drawable.ic_sad;
                                    /// satisfacotry.setText(f.toString());
                                    //  satisfacotry.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_satisfactory));

                                } else {
                                    imageId = R.drawable.ic_crying;
                                    //  bad.setText(f.toString());
                                    // bad.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_bad));

                                }

                            }
                        } catch (Exception e) {
                            //  Log.d(TAG,String.valueOf(e));
                        }
                        try{
                            seekBar.setThumb(getResources().getDrawable(imageId));
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        //  seekBar.setThumb(drawForSeekBar(v.getContext(),R.drawable.thumb_image_ic_cool));
                        while (it1.hasNext()) {
                            String data=it1.next();

                            if(data.equals("regno"))
                            {
                                continue;
                            }else
                                list.add(data);
                        }
                        for (String s : list) {
                            try {
                                map.put(s, job.getJSONObject(s));
                            } catch (JSONException e) {
                                //  Log.d(TAG+"Exception here",e.getMessage());
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
                        tabListener();
                        hideProgress();


                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
                    }


                }
                else{
                    hideProgress();
                    //progressBar.setVisibility(View.INVISIBLE);

                }

                Log.d(TAG+"mapData",myPercentage.toString());
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                hideProgress();
               // if(checkNet()) {
                    showPercentage();
              //  }
                // progressBar.setVisibility(View.INVISIBLE);
                // Log.d(TAG+"Exception","exception raised"+t.toString());
//                hideProgress();
//                ConnectionInterface connectionInterface= (ConnectionInterface) getActivity();
//                connectionInterface.reload();
                //Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();
            }
        });
    }
public  void tabListener()
{
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            try{
          String myTab=  tab.getText().toString().toLowerCase().replace(" ", "");
          Log.d(TAG+"tabChanged",myTab);
          Log.d(TAG+"tabChanged",myPercentage.get(myTab));
          showPercentage.setText(myPercentage.get(myTab));

        }catch (Exception e)
            {

            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });
}
    public void showProgress()
    {
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
    }
    public void hideProgress()
    {
        progressBar.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }


}