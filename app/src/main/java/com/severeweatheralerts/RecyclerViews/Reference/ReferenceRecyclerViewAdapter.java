package com.severeweatheralerts.RecyclerViews.Reference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.AlertCardClickListener;
import com.severeweatheralerts.TextGeneraters.Reference;

import java.util.ArrayList;
import java.util.Date;

public class ReferenceRecyclerViewAdapter extends RecyclerView.Adapter<ReferenceCardHolder> {
  private final ArrayList<Alert> referenceList;
  private AlertCardClickListener clickListener;

  public ReferenceRecyclerViewAdapter(ArrayList<Alert> referenceList) {
    this.referenceList = referenceList;
  }

  @NonNull
  @Override
  public ReferenceCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View referenceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reference, parent, false);
    return new ReferenceCardHolder(referenceView);
  }

  public void setClickListener(AlertCardClickListener clickListener) {
    this.clickListener = clickListener;
  }

  @Override
  public void onBindViewHolder(@NonNull ReferenceCardHolder holder, int position) {
    Alert reference = referenceList.get(position);
    holder.text.setText(new Reference(reference).getText(new Date()));
    holder.card.setCardBackgroundColor(new ReferenceColorChooser(reference).getColorAt(new Date()));
    holder.card.setOnClickListener(v -> {
      if (clickListener != null) clickListener.onCardClick(reference, holder);
    });
  }

  @Override
  public int getItemCount() {
    return referenceList.size();
  }
}
