package com.severeweatheralerts;

import android.content.Context;

import io.paperdb.Book;
import io.paperdb.Paper;

public class PaperDB {
  private static boolean isInitialized;
  public static Book getInstance(Context context) {
    if (!isInitialized) init(context);
    return Paper.book();
  }

  private static void init(Context context) {
    Paper.init(context);
    isInitialized = true;
  }
}
