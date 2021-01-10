package com.severeweatheralerts.RecyclerViews.Reference;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.severeweatheralerts.R;

public class ReferenceCardHolder extends ViewHolder {
  public CardView card;
  public TextView text;

  public ReferenceCardHolder(View view) {
    super(view);
    card = view.findViewById(R.id.reference_card);
    text = view.findViewById(R.id.reference_text);
  }
}
