package com.telran.ticketsapp.presentation.login.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.telran.ticketsapp.App;
import com.telran.ticketsapp.business.login.LoginInteractor;
import com.telran.ticketsapp.di.login.LoginModule;
import com.telran.ticketsapp.presentation.login.view.LoginView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {
    @Inject
    LoginInteractor interactor;
    Disposable disposable;

    public LoginPresenter() {
        App.get().plus(new LoginModule()).inject(this);
    }

    public void login(String email, String password){
     getViewState().showProgress();
        disposable = interactor.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoginSuccess,throwable -> onError(throwable.getMessage()));
    }

    public void toRegistration(){
        getViewState().showNextView();
    }

    public void recoveryNeeded(){
        getViewState().showRecoveryDialog();
    }

    public void recoverPassword(String email){
        getViewState().showProgress();
        disposable = interactor.recoverPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRecoverySuccess,throwable -> onError(throwable.getMessage()));

    }

    private void onLoginSuccess(){
        getViewState().hideProgress();
        getViewState().showPrevView();
    }

    private void onError(String error){
        getViewState().hideProgress();
        getViewState().showError(error);
    }

    private void onRecoverySuccess(){
        getViewState().hideProgress();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.get().clearLoginComponent();
    }
}
