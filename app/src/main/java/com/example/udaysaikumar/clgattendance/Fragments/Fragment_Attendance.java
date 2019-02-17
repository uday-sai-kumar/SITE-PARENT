package com.example.udaysaikumar.clgattendance.Fragments;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.udaysaikumar.clgattendance.Interfaces.ConnectionInterface;
import com.example.udaysaikumar.clgattendance.Others.DayDecorator;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.example.udaysaikumar.clgattendance.RetrofitPack.TimeStampClass;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Attendance extends Fragment {

  private   RetroGet retroGet;
  private   String API_KEY;
  private  String ATTENDANCE_COLLECTION;
 private   LinearLayout linearLayout;
  private MaterialCalendarView mCV;
   private String timestamp;
   private View v;
   private String UNAME;
    ImageView emoji;
    TextView myText;
   private int maxX,imageId;
    SeekBar seekBar;
    Float f;
    private String TAG="Fragment_Attendance_Log";

    private Calendar myCalendar;
private  TableLayout tableAttendance;
NestedScrollView nestedScrollView;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_fragment__attendance, container, false);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
seekBar=v.findViewById(R.id.seekBar);
myText=v.findViewById(R.id.myView);
nestedScrollView=v.findViewById(R.id.fragattend);

//textView1=v.findViewById(R.id.myView1);
//textView2=v.findViewById(R.id.myView2);
//textView3=v.findViewById(R.id.myView3);
//        tableAttendance=v.findViewById(R.id.tableAttendance);
        linearLayout=v.findViewById(R.id.attendacelayout);
        mCV=v.findViewById(R.id.calenderView);
tableAttendance=v.findViewById(R.id.tableAttendance);
        myCalendar=Calendar.getInstance();
        API_KEY=getResources().getString(R.string.APIKEY);
        ATTENDANCE_COLLECTION=getResources().getString(R.string.attendanceCollection);

        mCV.canScrollVertically(1);
        mCV.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
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
                                    //Log.d(TAG+1,String.valueOf(seekBar.getProgress()));
                                    //Log.d(TAG+1,String.valueOf(seekBar.getWidth()));
                                   // Log.d(TAG+1,String.valueOf(seekBar.getThumbOffset()));
                                    //Log.d(TAG+1,String.valueOf(seekBar.getMax()));
                                    int val = (seekBar.getProgress() * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                                    int textViewX = val - (myText.getWidth() / 2);
                                    int finalX = myText.getWidth() + textViewX > maxX ? (maxX - myText.getWidth()) : textViewX /*your margin*/;

                                    Log.d(TAG+1,String.valueOf(val));
                                    Log.d(TAG+1,String.valueOf(textViewX));
                                    Log.d(TAG+1,String.valueOf(finalX));
                                    //  Log.d("wholepositions3",String.valueOf(seekBar.getProgress()));
                                   // Log.d("wholepositions4",String.valueOf((seekBar.getWidth() - 2 * seekBar.getThumbOffset())));

                                //    Log.d("wholepositions5",String.valueOf(val));
                                  //  Log.d("wholepositions6",String.valueOf(textViewX));

                                    //Log.d("wholepositions7",String.valueOf(finalX));
                                    myText.setX(finalX < 0 ? 0/*your margin*/ : finalX);
                                    myText.setTextSize(25);

                                    int position1=position(32);
                                    int position2=position(70);
                                    int position3=position(87);

//                                    textView1.setX(position1<0?0:position1);
//                                    textView1.setText("A");
//                                    textView1.setTextSize(19);
//                                    textView2.setX(position2<0?0:position2);
//                                    textView2.setText("B");
//                                    textView2.setTextSize(19);
//                                    textView3.setX(position3<0?0:position3);
//                                    textView3.setText("C");
//                                    textView3.setTextSize(14);


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
               //progressAttendance.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                ConnectionInterface connectionInterface= (ConnectionInterface) getActivity();
                connectionInterface.reload();
                 }
        });


        //over here to get the percentage




       // finalattendance=v.findViewById(R.id.finalattendance);

        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        UNAME=sharedPreferences.getString("username","");
        //ATTENDANCE=sharedPreferences.getString("attendance","");

      //  finalattendance.setText("78 %");

       // System.out.println("dateis1hello  "+ );

     //   mCV.selectRange(CalendarDay.from(2019,1,2),CalendarDay.from(2019,1,20));
        //mCV.setDateSelected(new Date(),true);
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
            myCalendar.setTime(date);
            Log.d(TAG,String.valueOf(myCalendar.get(Calendar.MONTH))+" "+String.valueOf(myCalendar.get(Calendar.DATE)));
           // System.out.println("dateis1"+mCV.getMinimumDate() +"max"+mCV.getMaximumDate());
                mCV.setDateSelected(date,true);
                String month="12";
                String year="2018";
               // String month=String.valueOf(myCalendar.get(Calendar.MONTH)+1);
                //String year=String.valueOf(myCalendar.get(Calendar.YEAR));
            callAttendance(month+"-"+year);
          //  Calendar  calendar=Calendar.getInstance();
          //  Calendar.getInstance().set(2019,2,10);
          //  calendar.setTime(date);


//for(int i=1;i<=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);i++)
//                mCV.setDateSelected(CalendarDay.from(2019,1,i),true);

                //mCV.setDateSelected(CalendarDay.from(2019,2,3),true);

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

                //Toast.makeText(v.getContext(),mCV.getSelectedDate().toString(),Toast.LENGTH_SHORT).show();
             //  setFlags();
               try {
                   String adate = getDate(mCV.getSelectedDate().toString());
                   Log.d(TAG, adate);
               }catch (Exception E){
                   Log.d(TAG,E.getMessage());
               }
           // callAttendance(adate);
//commented here
            }
        });
        //progressAttendance.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
       // progressAttendance.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        ConnectionInterface connectionInterface= (ConnectionInterface) getActivity();
        connectionInterface.reload();
       // Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();

    }
});

        //progressAttendance.setVisibility(View.INVISIBLE);
        //linearLayout.setVisibility(View.VISIBLE);

        mCV.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Log.d(TAG,String.valueOf(date.getDay())+"  "+String.valueOf(date.getMonth())+" "+String.valueOf(date.getYear()));
            }
        });



        return v;
    }
    public int position(int ss)
    {
        int val = (ss * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        int textViewX = val - (myText.getWidth() / 2);
        int finalX = myText.getWidth() + textViewX > maxX ? (maxX - myText.getWidth()) : textViewX;
        return finalX;
    }
    public String getDate(String str){
        String[] s=str.replace("CalendarDay{","").replace("}","").split("-");
        String ss=s[2]+s[1]+s[0];
        int f=Integer.parseInt(s[1])+1;
        String ff=s[0].substring(0,4);
        System.out.println("hellojson"+s[2]+"/"+f+"/"+ff);
        return s[2]+"/"+f+"/"+ff;
    }
    public void callAttendance(final String adate){
        //final String p="{\"RegNo\":"+UNAME+",\"Date\":adate}";
//final String q="{\"RegNo\":+\"\"}";
       final String q= "{\"RegNo\":\""+UNAME+"\",\"Date\":\""+adate+"\"}";
        System.out.println("wowhellojson"+q);
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataAttendance = retroGet.getAttendance(ATTENDANCE_COLLECTION,API_KEY,q);
        dataAttendance.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                System.out.println("wowhellojson"+response.body());
                if(response.body()!=null)
                {
                    try {
                        if (!Objects.requireNonNull(response.body()).isEmpty()) {
                            String json = response.body();
                            System.out.println(TAG + json);
                            parseJson(json,adate);

//                            FragmentManager manager = getFragmentManager();
//
//                            FragmentTransaction fr = null;
//                            if (manager != null) {
//                                fr = manager.beginTransaction();
//                                MyDialogFragment fragment = new MyDialogFragment();
//                                Bundle b = new Bundle();
//                                b.putString("dialog", json);
//                                fragment.setArguments(b);
//                                fragment.show(fr, "myfrag");
//                                clearFlags();
//
//                            }


                        }
                    }catch (Exception e){
                        clearFlags();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
clearFlags();
               // Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();
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
public void parseJson(String json,String date)
{
    try {
        JSONArray jsonArray=new JSONArray(json);
        final JSONObject jsonObject= jsonArray.getJSONObject(0);
        final JSONObject jsonObject1=jsonObject.getJSONObject(date);
        final Iterator<String> iterator=jsonObject1.keys();
        while(iterator.hasNext())
        {
            String myDate=iterator.next();
            Log.d(TAG,myDate);
          //  DateFormat dateFormat=DateFormat.getDateInstance("dd-MM-yyyy",);
            DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
            try {
                Date date1=dateFormat.parse(myDate);
                Log.d(TAG,date1.toString());
                mCV.setDateSelected(date1,true);

                mCV.addDecorator(new DayDecorator(v.getContext(),mCV.getSelectedDate()));
            } catch (ParseException e) {
                Log.d(TAG,e.getMessage());
            }

        }
mCV.setOnDateChangedListener(new OnDateSelectedListener() {
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        tableAttendance.removeAllViewsInLayout();
        String day= String.valueOf(date.getDay());
        String month=String.valueOf(date.getMonth()+1);
        String year=String.valueOf(date.getYear());
       day= day.length()==1?"0"+day:day;
        month= month.length()==1?"0"+month:month;

        try {
            JSONObject jsonObject2=jsonObject1.getJSONObject(day+"-"+month+"-"+year);
            Iterator<String> iterator1=jsonObject2.keys();
            while (iterator1.hasNext())
            {
                String key=iterator1.next();
                Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
                TableRow tr = new TableRow(v.getContext());
               // tr.setPadding(5,0,0,0);
                //TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                //layoutParams.setMargins(10,0,0,1);
                TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,0,0,1);

               // tr.setLayoutParams(layoutParams);

                TextView t = new TextView(v.getContext());
                t.setText(key);
                t.setPadding(5,0,0,0);
                t.setGravity(Gravity.START);
                t.setTextColor(Color.BLACK);
                t.setBackgroundColor(Color.WHITE);
                // t.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                t.setTypeface(typeface);
                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tr.addView(t,layoutParams);
                TextView t1 = new TextView(v.getContext());
                t1.setText(jsonObject2.getString(key));
                t1.setGravity(Gravity.START);
                t1.setTextColor(Color.BLACK);
                t1.setBackgroundColor(Color.WHITE);
                // t.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                t1.setTypeface(typeface);
                t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tr.addView(t1,layoutParams);
                tableAttendance.addView(tr,layoutParams);
                //Log.d(TAG,iterator1.next());
            }
        } catch (JSONException e) {
            Log.d(TAG,e.getMessage());
        }
        nestedScrollView.fullScroll(View.FOCUS_DOWN);

        //Toast.makeText(v.getContext(),"date change"+date.getDay()+"/"+date.getMonth()+"/"+date.getYear(),Toast.LENGTH_SHORT).show();
    }
});
    } catch (JSONException e) {
        e.printStackTrace();
    }

}

}
