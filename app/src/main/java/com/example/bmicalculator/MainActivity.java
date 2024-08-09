package com.example.bmicalculator;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private MainActivity here;

    String website=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        here = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t = findViewById(R.id.textView8);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // API Call
                EditText editText = findViewById(R.id.editText);
                EditText editText2 = findViewById(R.id.editText2);
                String s1 =editText.getText().toString();
                String s2 =editText2.getText().toString();


                String link="http://webstrar99.fulton.asu.edu/page8/Service1.svc/calculateBMI?height="+s1+"&weight="+s2;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(link, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        TextView bmi = findViewById(R.id.textView7);
                        TextView message = findViewById(R.id.textView5);

                        try {
                            int temp =response.getInt("bmi");
                            if(temp<18){
                                bmi.setTextColor(getResources().getColor(R.color.blue));
                                message.setTextColor(getResources().getColor(R.color.blue));
                            }else if(temp>30){
                                bmi.setTextColor(getResources().getColor(R.color.red));
                                message.setTextColor(getResources().getColor(R.color.red));
                            }else if(temp>=18 && temp<25){
                                bmi.setTextColor(getResources().getColor(R.color.green));
                                message.setTextColor(getResources().getColor(R.color.green));

                            }else{
                                bmi.setTextColor(getResources().getColor(R.color.purple_500));
                                message.setTextColor(getResources().getColor(R.color.purple_500));
                            }
                            bmi.setText("BMI: "+String.valueOf(temp));
                            message.setText(response.getString("risk"));
                            JSONArray more = response.getJSONArray("more");
                            website=more.getString(0);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(here);
                requestQueue.add(jsonObjectRequest);
            }
        });
        TextView t2 = findViewById(R.id.textView10);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Website launch
                if(website==null){
                    return;
                }
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                startActivity(i);
            }
        });
    }
}