package com.severeweatheralerts.Networking.FetchServices;

import com.android.volley.VolleyError;

public interface RequestCallback {
  void success(Object response);
  void error(VolleyError error);
}
