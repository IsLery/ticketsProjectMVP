package com.telran.ticketsapp.presentation.selecttickets.view.halls;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SmallHall {

    public static TableLayout createSmallHallView(Context context){
        String[][] hallScheme = {
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
        };
        TableLayout tableLayout = new TableLayout(context);
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(30);
        textView.setText("Scene");
        tableLayout.addView(textView);

        for (int i = 0; i < hallScheme.length; i++) {
            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);

            for (int j = 0; j < hallScheme[i].length; j++) {
                ImageButton btn = new ImageButton(context);

                btn.setTag("Row: "+(i+1) + "Seat: "+(j+1));
                btn.setPadding(1,1,1,1);

                btn.setOnClickListener(v -> {
                    Toast.makeText(context,btn.getTag().toString(),Toast.LENGTH_SHORT).show();
                    Log.d("MY_TAG", "createView: "+btn.getTag());
                });

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    btn.setImageDrawable(context.getDrawable(R.drawable.ic_seat));
//                }

                tableRow.addView(btn);

            }
            tableLayout.addView(tableRow);
        }

        return tableLayout;
    }
}
