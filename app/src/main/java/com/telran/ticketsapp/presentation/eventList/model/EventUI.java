package com.telran.ticketsapp.presentation.eventList.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventUI {
    public String artist;
    public String title;
    public Date date;
    public String category;
    public String id;
    public String firstImageUrl;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstImageUrl() {
        return firstImageUrl;
    }

    public void setFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
    }

    public String getDateTxt() {
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        format.applyPattern("EEE, MMM d, yyyy");
        return format.format(date);
    }
}
