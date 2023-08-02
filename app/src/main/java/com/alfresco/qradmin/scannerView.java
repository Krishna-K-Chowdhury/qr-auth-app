package com.alfresco.qradmin;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import me.dm7.barcodescanner.zxing.ZXingScannerView;



public class scannerView extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    String t1;
    ZXingScannerView scannerView;

    private static final String apiurl=ApiUrls.QR_VERIFICATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                     permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    public void handleResult(Result rawResult) {

        t1 = rawResult.getText();

        String qry="?t1="+t1.trim();

        class verification extends AsyncTask<String,Void,String> {
            @Override
            protected  void onPostExecute(String data) {
                if(data.equals("found")) {

                    Toast.makeText(scannerView.this, "Verified / Allowed", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(), AllowedActivity.class));

                }
                else {
                    Toast.makeText(scannerView.this, "Not Allowed !!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), DeniedActivity.class));
                }
            }
            @Override
            protected String doInBackground(String... params) {
                String furl=params[0];

                try {
                    URL url=new URL(furl);
                    HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    return br.readLine();

                } catch (Exception ex) {
                    return ex.getMessage();
                }
            }
        }

        verification obj=new verification();
        obj.execute(apiurl+qry);

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}