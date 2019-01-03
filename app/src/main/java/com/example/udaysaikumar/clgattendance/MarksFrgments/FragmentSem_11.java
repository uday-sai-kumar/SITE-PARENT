package com.example.udaysaikumar.clgattendance.MarksFrgments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSem_11 extends Fragment {

    RetroGet retroGet;
    String API_KEY = "AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
    TableLayout tableLayout, headTable;
    TableLayout tableLayout1;
    JSONObject jj;
    List<String> st = new ArrayList<>();
    TextView subject, internals, externals, total;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_fragment_sem_11, container, false);
        tableLayout = v.findViewById(R.id.tables11);
        tableLayout1 = v.findViewById(R.id.tablesfinal);
        subject = v.findViewById(R.id.subject);
        internals = v.findViewById(R.id.internals);
        externals = v.findViewById(R.id.externals);
        total = v.findViewById(R.id.total);
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyLogin", MODE_PRIVATE);
        final String UNAME = sharedPreferences.getString("username", "");
        String PASS = sharedPreferences.getString("password", "");
        String MARKS = sharedPreferences.getString("marks", "");
        if (Integer.parseInt(UNAME.subSequence(0, 2).toString()) >= 15) {

            subject.setText(R.string.subject);
            internals.setText(R.string.internals);
            externals.setText(R.string.externals);
            total.setText(R.string.totalmarks);
        } else {

            subject.setText(R.string.subject);
            internals.setText(R.string.internals1);
            externals.setText(R.string.externals1);

            total.setText(R.string.totalmarks1);

            //tableLayout.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        String str = getArguments() != null ? getArguments().getString("data") : null;
        try {
            JSONObject jj=new JSONObject(str);
            Iterator<String> headers = jj.keys();

            while (headers.hasNext()) {
                String head = headers.next();
                st.add(head);
            }

            JSONObject jj1 = jj.getJSONObject(st.get(0));
            JSONObject jj2 = jj.getJSONObject(st.get(1));
            JSONObject jj3 = jj.getJSONObject(st.get(2));
            JSONObject jj4 = jj.getJSONObject(st.get(3));
            Iterator<String> it = jj1.keys();
            Iterator<String> it1 = jj2.keys();
            Iterator<String> it2 = jj3.keys();
            Iterator<String> it4 = jj4.keys();
            while (it.hasNext()) {
                String key = it.next();
                String key1 = it1.next();
                String key2 = it2.next();
                Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);

                TableRow tr = new TableRow(v.getContext());
               TableLayout.LayoutParams layoutParams=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
               layoutParams.setMargins(0,0,0,1);

                tr.setLayoutParams(layoutParams);

                TextView t = new TextView(v.getContext());
                t.setText(key);
                t.setTypeface(typeface);
                t.setTextColor(Color.BLACK);
                t.setGravity(Gravity.CENTER);
                t.setBackgroundColor(Color.WHITE);
                //t.setBackgroundResource(R.drawable.table_custom_text);
                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tr.addView(t);
                TextView t1 = new TextView(v.getContext());
              //  t1.setBackgroundResource(R.drawable.table_custom_text);
                t1.setText(jj1.get(key).toString());
                t1.setTypeface(typeface);
                t1.setBackgroundColor(Color.WHITE);
                t1.setGravity(Gravity.CENTER);
                t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tr.addView(t1);
                TextView t2 = new TextView(v.getContext());
                t2.setText(jj2.get(key1).toString());
                t2.setGravity(Gravity.CENTER);
                t2.setBackgroundColor(Color.WHITE);
              //  t2.setBackgroundResource(R.drawable.table_custom_text);
                t2.setTypeface(typeface);
                t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tr.addView(t2);
                TextView t3 = new TextView(v.getContext());
                t3.setText(jj3.get(key2).toString());
                t3.setTypeface(typeface);
                t3.setBackgroundColor(Color.WHITE);
                t3.setGravity(Gravity.CENTER);
              //  t3.setBackgroundResource(R.drawable.table_custom_text);
                t3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tr.addView(t3);
                tableLayout.addView(tr, layoutParams);
            }
            while (it4.hasNext()) {
                String key = it4.next();
                Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
                TableRow tr = new TableRow(v.getContext());
                TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,0,0,1);

                tr.setLayoutParams(layoutParams);

                TextView t = new TextView(v.getContext());
                t.setText(key);
                t.setGravity(Gravity.CENTER);
                t.setTextColor(Color.BLACK);
                t.setBackgroundColor(Color.WHITE);
                // t.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                t.setTypeface(typeface);
                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tr.addView(t);
                TextView t1 = new TextView(v.getContext());
                //t1.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                t1.setText(jj4.get(key).toString());
                t1.setGravity(Gravity.CENTER);
                t1.setBackgroundColor(Color.WHITE);
                t1.setTypeface(typeface);
                t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tr.addView(t1);
                tableLayout1.addView(tr, layoutParams);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return v;
    }

}
