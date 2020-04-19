package com.telran.ticketsapp.presentation.tickets.view.halls;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.telran.ticketsapp.R;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.data.tickets.dto.LockedSeats;
import com.telran.ticketsapp.data.tickets.dto.PriceRangeDto;

import java.util.List;

public class SmallHall {

    public static TableLayout createSmallHallView(Context context, HallStructureDto hallInfo, CompoundButton.OnCheckedChangeListener listener, List<String> checked){
        List<PriceRangeDto> ranges = hallInfo.getPriceRanges();
        List<LockedSeats> lockedSeats = hallInfo.getLockedSeats();


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
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(10);
        textView.setText("Scene");
        tableLayout.addView(textView);

        for (int i = 0; i < hallScheme.length; i++) {
            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            Pair<Integer,Double> rowInfo = findColorAndPrice(i+1,ranges);;
            int color = rowInfo.first;
            tableRow.setBackgroundColor(color);
            List<String> lockedInRow = getLockedSeatsInRow(lockedSeats, i);

            for (int j = 0; j < hallScheme[i].length; j++) {
          //      Button btn = new Button(context,null,R.style.Widget_MaterialComponents_Button,R.style.seat_button);
                CompoundButton btn = new CompoundButton(context, null, R.style.Widget_AppCompat_Button_Small, R.style.seat_button) {
                };

                if (lockedInRow != null && lockedInRow.contains(hallScheme[i][j]))
                {
                btn.setEnabled(false);
                btn.setBackgroundResource(R.drawable.seat_btn_disabled);
                } else {
                    btn.setText(hallScheme[i][j].substring(0,hallScheme[i][j].length()-1));
                }
//                btn.setMinimumWidth(10);
//                btn.setMinimumHeight(10);
                String tag = (i+1) + "-"+(j+1) + "-"+rowInfo.second;
                btn.setTag(tag);
                if (checked!= null && checked.contains(tag)){
                    btn.setChecked(true);
                }
             //  btn.setPadding(1,1,1,1);


                btn.setOnCheckedChangeListener(listener);

//                btn.setOnClickListener(v -> {
//                    Toast.makeText(context,btn.getTag().toString(),Toast.LENGTH_SHORT).show();
//                    boolean isChecked = btn.isChecked();
//                    btn.setChecked(!isChecked);
//                    Log.d("MY_TAG", "createView: "+btn.getTag());
//                });


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    btn.setImageDrawable(context.getDrawable(R.drawable.ic_seat));
//                }

                tableRow.addView(btn);

            }
            tableLayout.addView(tableRow);
        }

        return tableLayout;
    }

    private static List<String> getLockedSeatsInRow(List<LockedSeats> lockedSeats, int i) {
        for (LockedSeats s: lockedSeats
             ) {
            if (Integer.parseInt(s.getRow()) == i+1){
                return s.getSeats();
            }
        }
        return null;
    }

    private static Pair<Integer, Double> findColorAndPrice(int row, List<PriceRangeDto> ranges){
        for (PriceRangeDto r: ranges) {
            if (r.getParsedRows().contains(row)){
                return new Pair<Integer, Double>(Color.parseColor(r.getColor()),r.getPrice());
            }
        }
        return new Pair<Integer, Double>(Color.GRAY,0.00);
    }
}
