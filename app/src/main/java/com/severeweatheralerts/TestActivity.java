package com.severeweatheralerts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.severeweatheralerts.Adapters.AlertAdapter;
import com.severeweatheralerts.JSONParsing.AlertParser;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);

    // Instantiate the RequestQueue.
    RequestQueue queue = Volley.newRequestQueue(this);
    String url = "https://api.weather.gov/alerts?status=actual";

    // Request a string response from the provided URL.
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                AlertParser jap = new AlertParser(response);
                AlertAdapter aa = new AlertAdapter(jap.getParsedAlerts());
                ArrayList<Alert> alerts = aa.getAlerts();
                for (int i = 0; i < alerts.size(); i++) {
                  if (alerts.get(i).isLikelyLastUpdate()) {
                    String largeHeadline = alerts.get(i).getLargeHeadline();
                    if (largeHeadline != null) System.out.println(largeHeadline);
                    String smallHeadline = alerts.get(i).getSmallHeadline();
                    if (smallHeadline != null) System.out.println(smallHeadline);
                    String description = alerts.get(i).getDescription();
                    if (description != null) System.out.println(description);
                    String instruction = alerts.get(i).getInstruction();
                    if (instruction != null) System.out.println(instruction);
                    System.out.println("------------------");
                  }
                }
              }
            }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

      }
    });
    queue.add(stringRequest);
  }
}