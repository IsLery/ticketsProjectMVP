package com.telran.ticketsapp.presentation.registration.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.telran.ticketsapp.databinding.FragmentRegistrationBinding;
import com.telran.ticketsapp.presentation.eventList.view.EventListFragment;
import com.telran.ticketsapp.presentation.registration.presenter.RegistrationPresenter;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends MvpAppCompatFragment implements RegView, View.OnClickListener {

    AlertDialog errorDialog;
    FragmentRegistrationBinding binding;

    @InjectPresenter
    RegistrationPresenter presenter;


    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegistrationBinding.inflate(inflater,container,false);
      //  inputConfirmPassword = view.findViewById(R.id.inputConfirmPassword);

        binding.maleRadioBtn.setChecked(true);

        binding.registerBtn.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void showProgress() {
        binding.inputFirstName.setEnabled(false);
        binding.inputLastName.setEnabled(false);
        binding.inputEmail.setEnabled(false);
        binding.inputPhone.setEnabled(false);
        binding.inputPassword.setEnabled(false);
      //  inputConfirmPassword.setEnabled(false);
        binding.registerBtn.setEnabled(false);
        binding.maleRadioBtn.setEnabled(false);
        binding.femaleRadioBtn.setEnabled(false);
        binding.progressRegBar.setVisibility(View.VISIBLE);;
    }

    @Override
    public void hideProgress() {
        binding.inputFirstName.setEnabled(true);
        binding.inputLastName.setEnabled(true);
        binding.inputEmail.setEnabled(true);
        binding.inputPhone.setEnabled(true);
        binding.inputPassword.setEnabled(true);
      //  inputConfirmPassword.setEnabled(true);
        binding.registerBtn.setEnabled(true);
        binding.maleRadioBtn.setEnabled(true);
        binding.femaleRadioBtn.setEnabled(true);
        binding.progressRegBar.setVisibility(View.GONE);;

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
        Navigation.findNavController(binding.getRoot()).popBackStack();
     //   requireFragmentManager().beginTransaction().replace(R.id.root,new EventListFragment());
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
            int gender = binding.maleRadioBtn.isChecked() ? 1 : 2;
            String firstName = binding.inputFirstName.getText().toString();
            String lastName = binding.inputLastName.getText().toString();
            String email = binding.inputEmail.getText().toString();;
            String password = binding.inputPassword.getText().toString();;
            String phoneNumber = binding.inputPhone.getText().toString();;
            presenter.onRegistration( gender, firstName,
             lastName, email,
             password, phoneNumber);
        }
    }
    //TODO CHECK NAVIGATION!
}
