package com.severeweatheralerts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.severeweatheralerts.Adapters.AlertAdapter;
import com.severeweatheralerts.JSONParsing.AlertParser;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      RequestQueue queue = Volley.newRequestQueue(this);
      String url = "https://api.weather.gov/alerts?status=actual";
      StringRequest getRequest = new StringRequest(Request.Method.GET, url,
              new Response.Listener<String>()
              {
                @Override
                public void onResponse(String response) {
                  AlertParser ap = new AlertParser(response);
                  AlertAdapter aa = new AlertAdapter(ap.getParsedAlerts());
                  Location loc = new Location();
                  loc.addAlert(aa.getAlerts().get(0));
                  LocationsDao.getLocationList().add(loc);

                  Intent alertIntent = new Intent(MainActivity.this, AlertViewerActivity.class);
                  //holder.card.setTransitionName("zoom");
                  //Pair<View,String> pair1 = Pair.create((View)holder.card, "zoom");
                  //ActivityOptions aO = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pair1);
                  alertIntent.putExtra("locIndex", 0);
                  alertIntent.putExtra("alertIndex", 0);
                  startActivity(alertIntent);

                }
              },
              new Response.ErrorListener()
              {
                @Override
                public void onErrorResponse(VolleyError error) {
                  // TODO Auto-generated method stub
                }
              }
      ) {
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
          Map<String, String>  params = new HashMap<String, String>();
          params.put("User-Agent", "(https://www.github.com/qconrad/severe-weather-alerts Version 2.0 testing)");

          return params;
        }
      };
      queue.add(getRequest);
    }
}