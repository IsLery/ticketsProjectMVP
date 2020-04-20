package com.telran.ticketsapp.presentation.tickets.view.halls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.data.tickets.dto.LockedSeats;
import com.telran.ticketsapp.data.tickets.dto.PriceRangeDto;

import java.util.List;

public class BigHall {

    public static TableLayout createBigHallView(Context context, HallStructureDto hallInfo, CompoundButton.OnCheckedChangeListener listener, List<String> checked) {
        List<PriceRangeDto> ranges = hallInfo.getPriceRanges();
        List<LockedSeats> lockedSeats = hallInfo.getLockedSeats();
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        TableLayout right = new TableLayout(context);
        TableLayout center = new TableLayout(context);
        TableLayout left = new TableLayout(context);

        int[][] seatsPerSection = {
                {3,15,3},
                {4,16,4},
                {5,17,5},
                {6,18,6},
                {7,19,7},
                {8,20,8},
                {9,21,9},
                {10,22,10},
                {10,23,10},
                {9,22,9},
                {8,21,8},
                {7,20,7},
                {6,21,6},
                {5,22,5},
                {0,22,0}
        };

        String[][] halls = {
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","21L","21R","20R","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","21L","22L","21R","20R","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","20R","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"}
        };

        TableLayout.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(14);
        textView.setText("Scene");
        center.addView(textView);


        return null;

    }




    @SuppressLint("ShowToast")
    public static TableLayout createView(final Context context){
        int[][] seatsPerSection = {
                {3,15,3},
                {4,16,4},
                {5,17,5},
                {6,18,6},
                {7,19,7},
                {8,20,8},
                {9,21,9},
                {10,22,10},
                {10,23,10},
                {9,22,9},
                {8,21,8},
                {7,20,7},
                {6,21,6},
                {5,22,5},
                {0,22,0}
        };



        String[][] halls = {
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","21L","21R","20R","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","21L","22L","21R","20R","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","20R","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"}
        };

        String[][] hallMain = {
                {"4L","5L","6L","7L","8L","9L","10L","11L","10R","9R","8R","7R","6R","5R","4R"},
                {"5L","6L","7L","8L","9L","10L","11L","12L","12R","11R","10R","9R","8R","7R","6R","5R"},
                {"6L","7L","8L","9L","10L","11L","12L","13L","14L","13R","12R","11R","10R","9R","8R","7R","6R"},
                {"7L","8L","9L","10L","11L","12L","13L","14L","15L","15R","14R","13R","12R","11R","10R","9R","8R","7R"},
                {"8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","16R","15R","14R","13R","12R","11R","10R","9R","8R"},
                {"9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R"},
                {"10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R"},
                {"11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","21L","21R","20R","19R","18R","17R","16R","15R","14R","13R","12R","11R"},
                {"11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","21L","22L","21R","20R","19R","18R","17R","16R","15R","14R","13R","12R","11R"},
                {"10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","20L","20R","19R","18R","17R","16R","15R","14R","13R","12R","11R","10R"},
                {"9L","10L","11L","12L","13L","14L","15L","16L","17L","18L","19L","18R","17R","16R","15R","14R","13R","12R","11R","10R","9R"},
                {"8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","17R","16R","15R","14R","13R","12R","11R","10R","9R","8R"},
                {"7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","17L","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R"},
                {"6L","7L","8L","9L","10L","11L","12L","13L","14L","15L","16L","16R","15R","14R","13R","12R","11R","10R","9R","8R","7R","6R"},
                {"1L","2L","3L","4L","5L","6L","7L","8L","9L","10L","11L","10R","9R","8R","7R","6R","5R","4R","3R","2R","1R"}
        };
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
