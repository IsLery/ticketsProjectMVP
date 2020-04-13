package com.telran.ticketsapp.presentation.selecttickets.view.halls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class BigHall {


    @SuppressLint("ShowToast")
    public static TableLayout createView(final Context context){

        int[][] hall = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        TableLayout tableLayout = new TableLayout(context);
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(30);
        textView.setText("Scene");
        tableLayout.addView(textView);
        for (int i = 0; i < hall.length; i++) {
            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);

            for (int j = 0; j < hall[i].length; j++) {
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

    public static TableLayout sideLayoutHall(Context context, boolean isLeft){
        int[][] hall = {
                {1,2,3},
                {1,2,3,4},
                {1,2,3,4,5},
                {1,2,3,4,5,6},
                {1,2,3,4,5,6,7},
                {1,2,3,4,5,6,7,8},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9,10},
                {1,2,3,4,5,6,7,8,9,10},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8},
                {1,2,3,4,5,6,7},
                {1,2,3,4,5,6},
                {1,2,3,4,5},
        };
        float rotation = isLeft ? 35 : -35;
        TableLayout tableLayout = new TableLayout(context);
        for (int i = 0; i < hall.length; i++) {
            TableRow tableRow = new TableRow(context);
            if (i< 8){
                tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            }else if (isLeft) {
                tableRow.setGravity(Gravity.END);
            }else {
                tableRow.setGravity(Gravity.START);

            }
            tableRow.setDividerPadding(0);
            for (int j = 0; j < hall[i].length; j++) {
                ImageButton btn = new ImageButton(context);

                btn.setTag("Row: " + (i + 1) + "Seat: " + (j + 1));
                btn.setPadding(1, 1, 1, 1);

                btn.setOnClickListener(v -> {
                    Toast.makeText(context, btn.getTag().toString(), Toast.LENGTH_SHORT).show();
                    Log.d("MY_TAG", "createView: " + btn.getTag());
                });

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                  //  btn.setImageDrawable(context.getDrawable(R.drawable.ic_seat));
//                }

                tableRow.addView(btn);

            }
            tableLayout.addView(tableRow);
        }
        tableLayout.setRotation(rotation);
        return tableLayout;
    }


}
