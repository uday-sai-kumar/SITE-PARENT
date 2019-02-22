package com.example.udaysaikumar.clgattendance.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Feedback extends Fragment {
   private CardView appShare,email,whatsapp;

private TextInputEditText suggestion,bug;
private String mySuggesion,myBug;
  private   RatingBar rating;
  private MaterialButton materialButton;
  private boolean aBoolean=false;
  private RetroGet retroGet;
  private  View v;
  private ProgressBar myProgressBar;
  private String TAG="Fragment_Feedback_Log";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_fragment__feedback, container, false);
        appShare=v.findViewById(R.id.appshare);
        email=v.findViewById(R.id.bugreport);
        whatsapp=v.findViewById(R.id.bugwhatsapp);
        suggestion=v.findViewById(R.id.mySuggestion);
        bug=v.findViewById(R.id.myBugs);
        materialButton=v.findViewById(R.id.submit);
        rating=v.findViewById(R.id.myRating);
        myProgressBar=v.findViewById(R.id.myProgressBar);


        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (suggestion.getText().toString().isEmpty()) {
                        suggestion.setError("write suggestion");
                        aBoolean=false;
                    }else
                    {
                        mySuggesion=suggestion.getText().toString();
                        aBoolean=true;

                    }
                }
                catch (Exception e)
                {
                    suggestion.setError("write suggestion");
                }
                try {
                    if (bug.getText().toString().isEmpty()) {
                        bug.setError("write bug");
                        aBoolean=false;
                    }else {
                        aBoolean = true;
                        myBug=bug.getText().toString();
                    }
                }catch (Exception e){
                    bug.setError("write bug");

                }
                if(aBoolean) {
                  showProgress();

                    postFeedback(mySuggesion, myBug,rating.getRating());
                }
            }
        });

        appShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharing=new Intent(Intent.ACTION_SEND);
                sharing.setType("text/plain");
                sharing.putExtra(Intent.EXTRA_SUBJECT,"Share");
                sharing.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.redants.siteParent");
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
                   // sharing.setType("text/plain");
                    sharing.setDataAndType(Uri.parse("mailto:uforusk@gmail.com"),"text/plain");
                    sharing.putExtra(Intent.EXTRA_SUBJECT, "BUG REPORT");
                    startActivity(sharing);
                }
                catch (Exception e){

                }
            }
        });
        return v;
    }

   public void postFeedback(String suggetion,String bug,float rating)
{
    String keySuggestion="Suggestion";
    String keyBug="Bug";
    String myrating="Rating";
    String body = "{\""+keySuggestion+"\":\""+suggetion+"\",\""+keyBug+"\":\""+bug+"\",\""+myrating+"\":\""+rating+"\"}";
  //  Log.d(TAG,body);
    retroGet= RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
    Call<String> retroCall=retroGet.sendFeedback("application/json",body,getResources().getString(R.string.feedbackCollection),getResources().getString(R.string.APIKEY));
    retroCall.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
          //  Log.d(TAG,response.body());
hideProgress();
            Toast.makeText(v.getContext(),"posted successfully",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
hideProgress();
            Toast.makeText(v.getContext(),"failure",Toast.LENGTH_SHORT).show();

        }
    });
}
    public  void showProgress()
    {
        myProgressBar.setVisibility(View.VISIBLE);
        materialButton.setVisibility(View.INVISIBLE);
    }
    public void hideProgress()
    {
        myProgressBar.setVisibility(View.INVISIBLE);
        materialButton.setVisibility(View.VISIBLE);
    }

}
