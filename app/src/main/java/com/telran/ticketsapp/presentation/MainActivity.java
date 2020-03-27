package com.telran.ticketsapp.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.telran.ticketsapp.R;
import com.telran.ticketsapp.presentation.eventList.view.EventListFragment;
import com.telran.ticketsapp.presentation.login.view.LoginFragment;
import com.telran.ticketsapp.presentation.registration.view.RegistrationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.root,new EventListFragment())
                .commit();
    }
}
