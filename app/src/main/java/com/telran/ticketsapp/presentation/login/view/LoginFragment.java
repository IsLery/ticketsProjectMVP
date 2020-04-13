package com.telran.ticketsapp.presentation.login.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.presentation.login.presenter.LoginPresenter;
import com.telran.ticketsapp.presentation.registration.view.RegistrationFragment;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends MvpAppCompatFragment implements LoginView, View.OnClickListener {
    EditText inputEmail, inputPassword;
    Button loginBtn, registerBtn;
    TextView forgotPass;
    ProgressBar progressLogBar;
    AlertDialog recoverPassDialog;
    AlertDialog errorDialog;
    public static final String TAG = "Login_fragment_Tag";

    @InjectPresenter
    LoginPresenter presenter;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Log in:");
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        loginBtn = view.findViewById(R.id.loginBtn);
        registerBtn = view.findViewById(R.id.registerBtn);
        forgotPass = view.findViewById(R.id.forgotPass);
        progressLogBar = view.findViewById(R.id.progressLogBar);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        forgotPass.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerBtn:
                presenter.toRegistration();
                return;
            case R.id.loginBtn:
                String email = inputEmail.getText().toString();
                String pass = inputPassword.getText().toString();
                presenter.login(email,pass);
                return;
            case R.id.forgotPass:
                presenter.recoveryNeeded();

        }
    }

    @Override
    public void showProgress() {
        inputEmail.setEnabled(false);
        inputPassword.setEnabled(false);
        loginBtn.setEnabled(false);
        registerBtn.setEnabled(false);
        forgotPass.setEnabled(false);
        progressLogBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        inputEmail.setEnabled(true);
        inputPassword.setEnabled(true);
        loginBtn.setEnabled(true);
        registerBtn.setEnabled(true);
        forgotPass.setEnabled(true);
        progressLogBar.setVisibility(View.GONE);

    }

    @Override
    public void showError(String error) {
        errorDialog = new AlertDialog.Builder(getContext())
        .setTitle("Error")
        .setMessage(error)
                .setCancelable(false)
                .setPositiveButton("Ok",null)
                .create();
        errorDialog.show();
    }

    @Override
    public void showNextView() {
        requireFragmentManager().beginTransaction().replace(R.id.root,new RegistrationFragment()).addToBackStack("REG").commit();
    }

    @Override
    public void showPrevView() {
        Log.d(TAG, "showPrevView: ");
        getChildFragmentManager().popBackStack();
    }

    @Override
    public void hideErrorDialog() {
        if (errorDialog != null || errorDialog.isShowing()){
            errorDialog.dismiss();
            return;
        }
        if (recoverPassDialog != null || recoverPassDialog.isShowing()){
            recoverPassDialog.dismiss();
        }
    }

    @Override
    public void showRecoveryDialog() {
        View recView = getLayoutInflater().from(getContext()).inflate(R.layout.recover_pass,null);
        EditText recPass = recView.findViewById(R.id.emailForRecInput);
        recoverPassDialog = new AlertDialog.Builder(getContext())
                .setTitle("Password recovery")
                .setView(recView)
                .setPositiveButton("OK",((dialog, which) ->
                        presenter.recoverPassword(recPass.getText().toString())))
                .setNegativeButton("Cancel",null)

                .create();

        recoverPassDialog.show();
    }
}
