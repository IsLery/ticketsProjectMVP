package com.telran.ticketsapp.business.login;

import com.telran.ticketsapp.business.registration.InvalidFieldsException;
import com.telran.ticketsapp.data.login.LoginRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;



public class LoginInteractorImpl implements LoginInteractor{
    LoginRepository repository;

    public LoginInteractorImpl(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public Completable login(String email, String password) {
        email = email.trim();
        password = password.trim();
        try {
            validate(email, password);
            return repository.login(email, password);
        }catch (Throwable throwable){
            return Completable.error(throwable);
        }
    }

    @Override
    public Completable recoverPassword(String email) {
        email = email.trim();
        try {
            checkErrorsArray(validateEmail(email));
            return repository.recoverPass(email);
        }catch (Throwable throwable){
            return Completable.error(throwable);
        }
    }

    private void validate(String email, String password){
        List<String> errors = new ArrayList<>();
        errors.addAll(validateEmail(email));
        errors.addAll(validatePass(password));
        checkErrorsArray(errors);
    }

    private void checkErrorsArray(List<String> errors) {
        int size = errors.size();
        if (size > 0){
            String str = errors.get(size-1);
            str = str.replace("\n","");
            errors.set(size-1,str);
            StringBuilder sb = new StringBuilder();
            for (String s : errors) {
                sb.append(s);
            }
            throw new InvalidFieldsException(sb.toString());
        }
    }

    private List<String> validateEmail(String email){
        List<String> errors = new ArrayList<>();
        if (email.isEmpty()){
            errors.add("Email is required\n");
            return errors;
        }
        if (email.length() < 3 || email.length() > 100){
            errors.add("Email length should be between 3 and 100 characters\n");
        }
        if (!email.matches("(?:[a-zA-Z0-9.\\-]+)@(?:[a-z0-9\\-]+)\\.(?:[a-z0-9]{2,})")){
            errors.add("Email format is incorrect\n");
        }
        return errors;
    }

    private List<String> validatePass(String pass){
        List<String> errors = new ArrayList<>();
        if (pass.isEmpty()){
            errors.add("Password is required\n");
            return errors;
        }
        if (pass.length() < 8 || pass.length() > 25){
            errors.add("Password length should be between 8 and 25 characters\n");
        }
        return errors;
    }




}
