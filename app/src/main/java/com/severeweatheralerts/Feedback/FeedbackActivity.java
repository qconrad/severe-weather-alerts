package com.severeweatheralerts.Feedback;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Networking.FetchServices.RequestCallback;
import com.severeweatheralerts.Networking.PostService;
import com.severeweatheralerts.R;

public class FeedbackActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_feedback);
    showKeyboard();
  }

  private void showKeyboard() {
    if (findViewById(R.id.feedback_text).requestFocus()) {
      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
  }

  public void sendClick(View view) {
    hideKeyboard();
    EditText feedbackTV = findViewById(R.id.feedback_text);
    Spinner typeSelector = findViewById(R.id.type_selector);
    if (!validateInputs(feedbackTV)) return;
    setRefreshing(true);
    getToken(task -> sendFeedback(feedbackTV, typeSelector, task.getResult()));
  }

  private void hideKeyboard() {
    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  private void sendFeedback(EditText feedbackTV, Spinner typeSelector, String token) {
    String payload = new FeedbackPayloadGenerator(this, feedbackTV.getText().toString(), typeSelector.getSelectedItem().toString(), token).getJSONString();
    new PostService(this, Constants.FEEDBACK_URL, payload).request(new RequestCallback() {
      @Override
      public void success(Object response) {
        Toast.makeText(FeedbackActivity.this, "Thanks for your feedback!", Toast.LENGTH_SHORT).show();
        finish();
      }

      @Override
      public void error(VolleyError error) {
        setRefreshing(false);
        Toast.makeText(FeedbackActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void getToken(OnCompleteListener<String> completeListener) {
    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(completeListener);
  }

    private boolean validateInputs(EditText feedbackTV) {
    if (feedbackTV.length() < 5) {
      Toast.makeText(this, "This feedback isn't long enough.", Toast.LENGTH_SHORT).show();
      return false;
    } else if (feedbackTV.length() > 100000) {
      Toast.makeText(this, "Sorry, this feedback could not be sent because it is too long.", Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }

  private void setRefreshing(boolean isRefreshing) {
    if (isRefreshing) {
      findViewById(R.id.sendBtn).setVisibility(View.GONE);
      findViewById(R.id.feedback_progress).setVisibility(View.VISIBLE);
    } else {
      findViewById(R.id.sendBtn).setVisibility(View.VISIBLE);
      findViewById(R.id.feedback_progress).setVisibility(View.GONE);
    }
  }
}