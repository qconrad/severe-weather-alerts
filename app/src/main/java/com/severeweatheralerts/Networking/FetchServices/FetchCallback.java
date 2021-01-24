package com.severeweatheralerts.Networking.FetchServices;

import com.android.volley.VolleyError;

public interface FetchCallback {
  void success(Object response);
  void error(VolleyError error);
}
