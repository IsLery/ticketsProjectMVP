package com.telran.ticketsapp;

import android.app.Application;

import com.telran.ticketsapp.di.EventsDependenceFactory;
import com.telran.ticketsapp.di.LoginDependenceFactory;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new LoginDependenceFactory(this);
        new EventsDependenceFactory(this);
    }
}
