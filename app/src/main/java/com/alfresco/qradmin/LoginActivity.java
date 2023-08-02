package com.alfresco.qradmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    private static final String apiurl=ApiUrls.ADMIN_LOGIN;
    EditText t1,t2;
    TextView textView;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_admin);

        checklogoutmsg(textView);
    }

    public void login(View view) {

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        textView = findViewById(R.id.tv);

        String qry="?t1="+t1.getText().toString().trim()+"&t2="+t2.getText().toString().trim();

        class dbprocess extends AsyncTask<String,Void,String> {
            @Override
            protected  void onPostExecute(String data) {
                if(data.equals("found")) {
                    SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("uname",t1.getText().toString());
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else {
                    t1.setText("");
                    t2.setText("");
                    textView.setTextColor(Color.parseColor("#8B0000"));
                    textView.setText(data);
                }
            }
            @Override
            protected String doInBackground(String... params) {
                String furl=params[0];

                try {
                    URL url=new URL(furl);
                    HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    return br.readLine();

                }catch (Exception ex) {
                    return ex.getMessage();
                }
            }
        }

        dbprocess obj=new dbprocess();
        obj.execute(apiurl+qry);

    }

    public void checklogoutmsg(View view) {
        textView=(TextView)findViewById(R.id.tv);

        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("msg")) {
            textView.setText(sp.getString("msg", ""));
            SharedPreferences.Editor ed=sp.edit();
            ed.remove("msg");
            ed.apply();
        }
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}