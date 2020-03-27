package com.telran.ticketsapp.presentation.eventList.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.data.eventsList.models.EventDto;


import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventHolder> {


    private List<EventDto> eventsAll = new ArrayList<>();


    private Context context;
    public static final String TAG = "EventListAdapter";

    public EventListAdapter( Context context) {
        this.context = context;
    }

    public void updateEvents(List<EventDto> moreEvents){
        eventsAll.addAll(moreEvents);
        notifyDataSetChanged();
    }


    public void setEvents(List<EventDto> events) {
        //чтоб зря не перерисовывать
        if (eventsAll == events){
            return;
        }
        eventsAll.clear();
        eventsAll.addAll(events);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.event_row,parent,false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        EventDto event = eventsAll.get(position);
        Log.d(TAG, "adapter onBindViewHolder: "+event + "position: " + position);
        holder.bind(event);

    }

    @Override
    public int getItemCount() {
        if (eventsAll!= null) {
            return eventsAll.size();
        }
        return 0;
    }

    class EventHolder extends RecyclerView.ViewHolder{
        TextView eventTitleTxt, artistTxt, eventDateTxt;
        ImageView eventImage;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventTitleTxt = itemView.findViewById(R.id.eventTitleTxt);
            artistTxt = itemView.findViewById(R.id.artistTxt);
            eventDateTxt  = itemView.findViewById(R.id.eventDateTxt);
            eventImage = itemView.findViewById(R.id.eventImage);
        }

        public void bind(EventDto event){
            eventTitleTxt.setText(event.getEventName());
            artistTxt.setText(event.getArtist());
            eventDateTxt.setText(event.getDateTxt());
            String imgUrl = "https://i2.pickpik.com/photos/856/116/53/concert-party-island-festival-preview.jpg";
            if (event.getImages().size() > 0) {
                imgUrl = event.getImages().get(0);
            }
            Picasso.get()
                    .load(imgUrl)
                    .into(eventImage);

        }
//        private EventRowBinding binding;
//
//        public EventHolder(EventRowBinding binding) {
//            super(binding.getRoot());
//
//        }
//
//        public void bind(EventDto event){
//        //    binding.artistTxt.setText(event.getArtist());
//         //   binding.eventTitleTxt.setText(event.getEventName());
//            binding.eventDateTxt.setText(event.getDateTxt());
//        }
    }
}
