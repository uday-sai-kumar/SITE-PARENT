package com.example.udaysaikumar.clgattendance.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.example.udaysaikumar.clgattendance.RetrofitPack.TimeStampClass;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Attendance extends Fragment {

    RetroGet retroGet;
    String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
ProgressBar progressAttendance;
LinearLayout linearLayout;
    TableLayout tableAttendance;
    MaterialCalendarView mCV;
    String timestamp;
    View v;
    String UNAME;
    TextView bad,satisfacotry,excellent;
    String ATTENDANCE;
   // CircleDisplay cd;
    ImageView emoji;
    TextView myText;
    int maxX;
    int imageId;
    SeekBar seekBar;
  //  ViewTreeObserver viewTreeObserver;
    Float f;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_fragment__attendance, container, false);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
seekBar=v.findViewById(R.id.seekBar);
myText=v.findViewById(R.id.myView);
       // bad=v.findViewById(R.id.bad);
       // satisfacotry=v.findViewById(R.id.satisfactory);
      //  excellent=v.findViewById(R.id.excellent);
        tableAttendance=v.findViewById(R.id.tableAttendance);
        progressAttendance=v.findViewById(R.id.progressAttendance);
        linearLayout=v.findViewById(R.id.attendacelayout);
        mCV=v.findViewById(R.id.calenderView);
        mCV.canScrollVertically(1);
        //cd=v.findViewById(R.id.circledisplay);
       // cd.setValueWidthPercent(10f);
        //cd.setTextSize(30f);
       // cd.setColor(Color.RED);
       // cd.setDrawText(true);
       // cd.setFormatDigits(2);
       // cd.setUnit("%");
       // cd.setStepSize(0.08f);
        progressAttendance.setIndeterminate(true);
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
maxX=point.x;
       // progressAttendance.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferencess=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        UNAME=sharedPreferencess.getString("username","");
        String qq="{\"regno\":{$eq:\""+UNAME+"\"}}";
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        //linearLayout.setVisibility(View.INVISIBLE);

        Call<String> dataAttendance = retroGet.getPercentage("PERCENTAGE",API_KEY,qq);
        dataAttendance.enqueue(new Callback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                assert response.body() != null;
                String json=response.body();
                assert json != null;
                if(!json.isEmpty()){
                    try {
                       JSONArray jsonArray = new JSONArray(json);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        Iterator<String>iterator=jsonObject.keys();
                        String st=null;
                        while (iterator.hasNext()){
                             st=iterator.next();
                        }
                        String jsonObject1=jsonObject.get(st).toString();

                       f=  Float.parseFloat(jsonObject1);

                       // cd.showValue(f, 100, false);
                        if(f>=75){
                            imageId=R.drawable.ic_cool;
                            //excellent.setText(f.toString());
                            //excellent.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_excellent));

                        }else if(f>65){
                            imageId=R.drawable.ic_sad;
                          //  satisfacotry.setText(f.toString());
                          //  excellent.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_satisfactory));

                        }
                        else {
                            imageId=R.drawable.ic_crying;
                           // bad.setText(f.toString());
                           // excellent.setBackground(getResources().getDrawable(R.drawable.circle_aggregade_bad));

                        }
                        myText.setText(f.toString());
                        seekBar.setProgress(Math.round(f));
                     final ViewTreeObserver viewTreeObserver=seekBar.getViewTreeObserver();

                        if(viewTreeObserver.isAlive())
                        {
                            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    //Log.d("wholepositions1",String.valueOf(maxX));
                                    //Log.d("wholepositions2",String.valueOf(seekBar.getWidth()));
                                    //Log.d("wholepositions3",String.valueOf(seekBar.getThumbOffset()));
                                    int val = (seekBar.getProgress() * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                                  //  Log.d("wholepositions3",String.valueOf(seekBar.getProgress()));
                                   // Log.d("wholepositions4",String.valueOf((seekBar.getWidth() - 2 * seekBar.getThumbOffset())));

                                    int textViewX = val - (myText.getWidth() / 2);
                                //    Log.d("wholepositions5",String.valueOf(val));
                                  //  Log.d("wholepositions6",String.valueOf(textViewX));

                                    int finalX = myText.getWidth() + textViewX > maxX ? (maxX - myText.getWidth()) : textViewX /*your margin*/;
                                    //Log.d("wholepositions7",String.valueOf(finalX));
                                    myText.setX(finalX < 0 ? 0/*your margin*/ : finalX);
                                    myText.setTextSize(25);
                                    //Log.d("wholepositions","x is"+String.valueOf(v.getX())+"y is "+String.valueOf(v.getX()));

                                    seekBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                }
                            });

                        }
                        seekBar.setThumb(getResources().getDrawable(imageId));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                                   }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();
               progressAttendance.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                 }
        });
       // finalattendance=v.findViewById(R.id.finalattendance);

        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        UNAME=sharedPreferences.getString("username","");
        ATTENDANCE=sharedPreferences.getString("attendance","");

      //  finalattendance.setText("78 %");

retroGet= TimeStampClass.getTimestamp().create(RetroGet.class);
Call<String> stringCall=retroGet.getTimeStamp();
stringCall.enqueue(new Callback<String>() {
    @Override
    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
        String json=response.body();
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.getJSONArray("zones");
            JSONObject jsonObject1=jsonArray.getJSONObject(0);
           timestamp=jsonObject1.getString("timestamp");
           //System.out.println("dateis"+System.currentTimeMillis());
            Timestamp time=new Timestamp((Long.parseLong(timestamp)-19800)*1000L);
            Date date=new Date(time.getTime());
            System.out.println("dateis1"+date);
                mCV.setDateSelected(date,true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    final CalendarDay calendarDay=mCV.getSelectedDate();

mCV.addDecorator(new DayDecorator(v.getContext(),calendarDay));

      //System.out.println("currentdate"+mCV.getSelectedDate());

       // callAttendance();
        mCV.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

               // Toast.makeText(v.getContext(),mCV.getSelectedDate().toString(),Toast.LENGTH_SHORT).show();
                setFlags();
            String adate=getDate(mCV.getSelectedDate().toString());
            callAttendance(adate);

            }
        });
        progressAttendance.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
        progressAttendance.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();

    }
});

        //progressAttendance.setVisibility(View.INVISIBLE);
        //linearLayout.setVisibility(View.VISIBLE);
        return v;
    }
    public String getDate(String str){
        String[] s=str.replace("CalendarDay{","").replace("}","").split("-");
        String ss=s[2]+s[1]+s[0];
        int f=Integer.parseInt(s[1])+1;
        String ff=s[0].substring(0,4);
        System.out.println("hellojson"+s[2]+"/"+f+"/"+ff);
        return s[2]+"/"+f+"/"+ff;
    }
    public void callAttendance(String adate){
        String course="BTECH";
        String year="2015";
        String branch="CSE";
        String section="A";
       final String q= "{\"date\":\""+adate+"\",\"course\":\""+course+"\",\"year\":\""+year+"\","+"\"branch\":\""+branch+"\","+"\"section\":\""+section+"\"}";
        System.out.println("wowhellojson"+q);
        //final String q="{\"regno\":{$eq:\""+UNAME+"\"},\"date\":{$eq:\""+adate+"\"}}";
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataAttendance = retroGet.getAttendance("DAILY_ATTENDANCE",API_KEY,q);
        dataAttendance.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                System.out.println("wowhellojson"+response.body());
                if(response.body()!=null)
                {
                    if(!Objects.requireNonNull(response.body()).isEmpty())
                    {
                        String json=response.body();
                        System.out.println("wowhellojson"+json);
                        FragmentManager manager=getFragmentManager();
                        try {
                            FragmentTransaction fr = null;
                            if (manager != null) {
                                fr = manager.beginTransaction();
                                MyDialogFragment fragment = new MyDialogFragment();
                                Bundle b = new Bundle();
                                b.putString("dialog", json);
                                fragment.setArguments(b);
                                fragment.show(fr, "myfrag");
                                clearFlags();

                            }

                        }catch (Exception e){
                           clearFlags();
                        }

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
clearFlags();
                Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void setFlags(){
        try {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }catch (Exception e){}
    }
public void clearFlags()
{
    try {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }catch (Exception e){}

}

}
