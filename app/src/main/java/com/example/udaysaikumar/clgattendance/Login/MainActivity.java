package com.example.udaysaikumar.clgattendance.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroServer;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitOPTServer;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RetroGet retroGet;
    EditText phone;
    String phoneNo;
    List<PhoneData> list;
    Button login;
    String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
    ProgressBar progressBar;
    String COLLECTION="PHONENUMBERS";
    void enableView(boolean b)
    {
        if(b)
        {
            phone.setEnabled(true);
            login.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);

        }
        else
        {
            phone.setEnabled(false);
            login.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        phone=findViewById(R.id.pNo);
        progressBar=findViewById(R.id.progressBar);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (phone.getText().toString().equals("")|| phone.getText().length()<=9) {
                    Toast.makeText(MainActivity.this,"please enter valid number",Toast.LENGTH_SHORT).show();
                }
                else{

                   enableView(false);
                    phoneNo = phone.getText().toString();

                    //final String p="{\"parentmobile\":{$eq:\""+phoneNo+"\"}}";
                   final String p="{\"studentmobile\":{$eq:\""+phoneNo+"\"}}";
                    retroGet = RetroServer.getRetrofit().create(RetroGet.class);
                    Call<List<PhoneData>> dataCall = retroGet.getPhoneData(COLLECTION,API_KEY,p);
                    dataCall.enqueue(new Callback<List<PhoneData>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<PhoneData>> call, @NonNull Response<List<PhoneData>> response) {
                            if (response.body()!= null ) {
                                list = response.body();
                                System.out.println("ourresponse"+list);
                                try{
                                if (!list.isEmpty()) {
                                    if (phoneNo.equals(list.get(0).getStudentmobile())) {
                                        SharedPreferences s = getSharedPreferences("MyLogin", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = s.edit();
                                        editor.putString("phone", phoneNo);
                                        editor.putString("username",list.get(0).getRegno());
                                        editor.apply();
                                     /*   Random random=new Random();
                                        int n=random.nextInt(7000)+1000;
                                        String otp="Your one time password is \n"+n;

                                        editor.putInt("otp",n);
                                        editor.apply();
                                        retroGet= RetrofitOPTServer.getRetrofitOTP().create(RetroGet.class);
                                        String un="sasicollege",pass="SITE2002",from="INSITE",to=phoneNo,type="1";
                                        Call<String> retro=retroGet.getOTP(un,pass,from,to,otp,type);
                                        retro.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                                                //  Toast.makeText(view.getContext(), response.body(),Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                                                //Toast.makeText(view.getContext(),"Good",Toast.LENGTH_SHORT).show();
                                            }
                                        });*/
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Intent i = new Intent(MainActivity.this, OTPActivity.class);
                                        startActivity(i);
                                        finish();
                                        //Toast.makeText(MainActivity.this,"LoginSuccess",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        System.out.println("ourresponse 1"+list);

                                       fun();
                                    }
                                }
                                else {
                                    System.out.println("ourresponse 2"+list);

                                    fun();
                                }

                            }
                            catch (Exception e){

                            }

                            }


                           /* else
                            {
                                System.out.println("ourresponse 3"+list);

                              //  hello(q);
                            }*/
                           enableView(true);

                        }
                      /*  private void hello(String query){
                            retroGet = RetroServer.getRetrofit().create(RetroGet.class);
                            System.out.println("inhello");
                            Call<List<PhoneData>> dataCall = retroGet.getPhoneData("PHONENUMBERS",API_KEY,query);
                            dataCall.enqueue(new Callback<List<PhoneData>>() {
                                @Override
                                public void onResponse(@NonNull Call<List<PhoneData>> call, @NonNull Response<List<PhoneData>> response) {
                                    try {
                                        if (response.body() != null & !response.body().isEmpty()) {
                                            list = response.body();
                                            if (list != null && !list.isEmpty()) {
                                                if (phoneNo.equals(list.get(0).getParentmobile())) {
                                                    SharedPreferences s = getSharedPreferences("MyLogin", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = s.edit();
                                                    Random random = new Random();
                                                    int n = random.nextInt(7000) + 1000;
                                                    String otp = "Your one time password is \n" + n;
                                                    editor.putString("username", list.get(0).getRegno());
                                                    editor.putString("phone", phoneNo);
                                                    editor.putInt("otp", n);
                                                    editor.apply();
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    retroGet = RetrofitOPTServer.getRetrofitOTP().create(RetroGet.class);
                                                    String un = "sasicollege", pass = "SITE2002", from = "INSITE", to = phoneNo, type = "1";
                                                    Call<String> retro = retroGet.getOTP(un, pass, from, to, otp, type);
                                                    retro.enqueue(new Callback<String>() {
                                                        @Override
                                                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {


                                                        }

                                                        @Override
                                                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                                                        }
                                                    });
                                                    Intent i = new Intent(MainActivity.this, OTPActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            }
                                        } else {
                                            fun();
                                        }
                                    }catch (Exception e){

                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<List<PhoneData>> call, @NonNull Throwable t) {
                                    Toast.makeText(MainActivity.this, "Failure, please connect to active network", Toast.LENGTH_LONG).show();

                                }
                            });
                        }*/

                        @Override
                        public void onFailure(@NonNull Call<List<PhoneData>> call, @NonNull Throwable t) {
                          enableView(true);
                            Toast.makeText(MainActivity.this, "Failure, please connect to active network", Toast.LENGTH_LONG).show();

                        }

                    });

                }
            }
        });


    }
    void fun(){
        enableView(true);
        Toast.makeText(MainActivity.this, "parent number not registered", Toast.LENGTH_LONG).show();
    }

}
