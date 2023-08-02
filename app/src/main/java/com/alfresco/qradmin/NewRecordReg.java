package com.alfresco.qradmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NewRecordReg extends AppCompatActivity {

    EditText nameid, rollid, yearid, deptid,emailET, paymentET, payRecNameEt;

    Button submitBt, dashBt;

    boolean isAllFieldsChecked = false;

    private static final String url=ApiUrls.NEW_RECORD_REGISTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameid=findViewById(R.id.nameid);
        rollid=findViewById(R.id.rollid);
        yearid=findViewById(R.id.yearid);
        deptid=findViewById(R.id.dept);
        emailET=findViewById(R.id.emailid);
        paymentET=findViewById(R.id.transacid);
        payRecNameEt=findViewById(R.id.payRecName);

        submitBt=findViewById(R.id.register);
        dashBt=findViewById(R.id.dashBd);

        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                if(isAllFieldsChecked) {

                    register_user(nameid.getText().toString(), rollid.getText().toString(), yearid.getText().toString(), deptid.getText().toString(), emailET.getText().toString(), paymentET.getText().toString(), payRecNameEt.getText().toString());
                }
            }
        });

        dashBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }


    public void register_user(final String name, final String roll, final String year, final String dept, final String email, final String transacinfo, final String payReceiver){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                nameid.setText("");
                rollid.setText("");
                yearid.setText("");
                deptid.setText("");
                emailET.setText("");
                paymentET.setText("");
                payRecNameEt.setText("");

                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                if (response.equals("inserted")){
                    startActivity(new Intent(getApplicationContext(),SpotPaymentActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                nameid.setText("");
                rollid.setText("");
                yearid.setText("");
                deptid.setText("");
                emailET.setText("");
                paymentET.setText("");
                payRecNameEt.setText("");

            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("name",name);
                map.put("roll",roll);
                map.put("year",year);
                map.put("dept",dept);
                map.put("email",email);
                map.put("transacinfo",transacinfo);
                map.put("payreceiver",payReceiver);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    private boolean CheckAllFields(){
        if(nameid.length()==0){
            nameid.setError("Name is required!");
            return false;
        }if(rollid.length()==0){
            rollid.setError("Roll no is required!");
            return false;
        }if(yearid.length()==0){
            yearid.setError("Year required!");
            return false;
        }if(deptid.length()==0){
            deptid.setError("Department required!");
            return false;
        }if(emailET.length()==0){
            emailET.setError("Email id required!");
            return false;
        }if(paymentET.length()==0){
            paymentET.setError("UPI / CASH info required!");
            return false;
        }if(payRecNameEt.length()==0){
            payRecNameEt.setError("Payment receiver name required!");
            return false;
        }
        return true;
    }

}
