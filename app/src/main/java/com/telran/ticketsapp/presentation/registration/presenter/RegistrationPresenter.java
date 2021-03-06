package com.telran.ticketsapp.presentation.registration.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.telran.ticketsapp.App;
import com.telran.ticketsapp.business.registration.RegInteractor;
import com.telran.ticketsapp.di.registration.RegistrationModule;
import com.telran.ticketsapp.presentation.registration.view.RegView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegView> {
    @Inject
    public RegInteractor interactor;

    private Disposable disposable;
    public RegistrationPresenter() {
        App.get().plus(new RegistrationModule()).inject(this);
    }

    public void onRegistration(int gender,
                               String firstName,
                               String lastName,
                               String email,
                               String password,
                               String phoneNumber){
        getViewState().showProgress();
         disposable = interactor.onRegistration(gender, firstName,
                lastName, email,
                password, phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::regSuccess,throwable -> regError(throwable.getMessage()));

    }

    public void onDialogClicked(){
        getViewState().hideErrorDialog();
    }



    private void regSuccess(){
        getViewState().hideProgress();
        getViewState().showNextView();
    }

    private void regError(String error){
        getViewState().hideProgress();
        getViewState().showError(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable!= null) {
            disposable.dispose();
        }
        App.get().clearRegistrationComponent();
    }
}
