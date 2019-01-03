package com.example.udaysaikumar.clgattendance.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.udaysaikumar.clgattendance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Feedback extends Fragment {
    CardView appShare,email,whatsapp;


    public Fragment_Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fragment__feedback, container, false);
        appShare=v.findViewById(R.id.appshare);
        email=v.findViewById(R.id.bugreport);
        whatsapp=v.findViewById(R.id.bugwhatsapp);
        appShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharing=new Intent(Intent.ACTION_SEND);
                sharing.setType("text/plain");
                sharing.putExtra(Intent.EXTRA_SUBJECT,"Share");
                sharing.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.redants.site");
                startActivity(Intent.createChooser(sharing,"Share"));
            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=919494345589&text=BUGREPORT");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                catch (Exception e){

                }
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=null;
                try {

                    Intent sharing = new Intent(Intent.ACTION_SENDTO);
                    sharing.setType("text/plain");
                    sharing.setData(Uri.parse("mailto:uforusk@gmail.com"));
                    sharing.putExtra(Intent.EXTRA_SUBJECT, "BUG REPORT");
                    startActivity(sharing);
                }
                catch (Exception e){

                }
            }
        });
        return v;
    }

}
