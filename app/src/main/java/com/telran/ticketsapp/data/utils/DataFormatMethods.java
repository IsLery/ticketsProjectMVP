package com.telran.ticketsapp.data.utils;

import com.google.gson.Gson;
import com.telran.ticketsapp.data.login.models.ErrorDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormatMethods {
    public static String parseErrorMsg(String string) {
        Gson gson = new Gson();
        ErrorDto errorDto = gson.fromJson(string,ErrorDto.class);
        return errorDto.getMessage();
    }

    public static String getDateTxt(String pattern, long eventStart) {
        Date date = new Date();
        date.setTime(eventStart);
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        format.applyPattern(pattern);
        return format.format(date);
    }
}
