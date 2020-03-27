package com.telran.ticketsapp.presentation.registration.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.presentation.registration.presenter.RegistrationPresenter;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends MvpAppCompatFragment implements RegView, View.OnClickListener {
    EditText inputFirstName, inputLastName, inputEmail, inputPhone, inputPassword/*, inputConfirmPassword*/;
    Button registerBtn;
    RadioButton maleRadioBtn, femaleRadioBtn;
    ProgressBar progressRegBar;
    AlertDialog errorDialog;

    @InjectPresenter
    RegistrationPresenter presenter;


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        inputFirstName = view.findViewById(R.id.inputFirstName);
        inputLastName = view.findViewById(R.id.inputLastName);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPhone = view.findViewById(R.id.inputPhone);
        inputPassword = view.findViewById(R.id.inputPassword);
      //  inputConfirmPassword = view.findViewById(R.id.inputConfirmPassword);
        registerBtn = view.findViewById(R.id.registerBtn);
        maleRadioBtn = view.findViewById(R.id.maleRadioBtn);
        maleRadioBtn.setChecked(true);
        femaleRadioBtn = view.findViewById(R.id.femaleRadioBtn);
        progressRegBar = view.findViewById(R.id.progressRegBar);

        registerBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void showProgress() {
        inputFirstName.setEnabled(false);
        inputLastName.setEnabled(false);
        inputEmail.setEnabled(false);
        inputPhone.setEnabled(false);
        inputPassword.setEnabled(false);
      //  inputConfirmPassword.setEnabled(false);
        registerBtn.setEnabled(false);
        maleRadioBtn.setEnabled(false);
        femaleRadioBtn.setEnabled(false);
        progressRegBar.setVisibility(View.VISIBLE);;
    }

    @Override
    public void hideProgress() {
        inputFirstName.setEnabled(true);
        inputLastName.setEnabled(true);
        inputEmail.setEnabled(true);
        inputPhone.setEnabled(true);
        inputPassword.setEnabled(true);
      //  inputConfirmPassword.setEnabled(true);
        registerBtn.setEnabled(true);
        maleRadioBtn.setEnabled(true);
        femaleRadioBtn.setEnabled(true);
        progressRegBar.setVisibility(View.GONE);;

    }

    @Override
    public void showError(String error) {
        errorDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setPositiveButton("Ok", ((dialog, which) -> presenter.onDialogClicked()))
                .setTitle("Error")
                .setMessage(error)
                .setCancelable(false)
                .create();
        errorDialog.show();
    }

    @Override
    public void showNextView() {
        //TODO add navigator
        Toast.makeText(getContext(), "SHOW NEXT VIEW", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideErrorDialog() {
        if (errorDialog != null && errorDialog.isShowing()){
            errorDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerBtn){
            int gender = maleRadioBtn.isChecked() ? 1 : 2;
            String firstName = inputFirstName.getText().toString();
            String lastName = inputLastName.getText().toString();
            String email = inputEmail.getText().toString();;
            String password = inputPassword.getText().toString();;
            String phoneNumber = inputPhone.getText().toString();;
            presenter.onRegistration( gender, firstName,
             lastName, email,
             password, phoneNumber);
        }
    }
}
