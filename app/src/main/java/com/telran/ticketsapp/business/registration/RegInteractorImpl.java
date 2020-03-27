package com.telran.ticketsapp.business.registration;

import com.telran.ticketsapp.data.registration.RegRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;

public class RegInteractorImpl implements RegInteractor {
    RegRepository repository;

    public RegInteractorImpl(RegRepository repository) {
        this.repository = repository;
    }

    @Override
    public Completable onRegistration(int gender, String firstName, String lastName, String email, String password, String phoneNumber) {
        try {
            firstName = firstName.trim();
            lastName = lastName.trim();
            email = email.trim();
            password = password.trim();
            phoneNumber = formatPhone(phoneNumber.trim());

            validate(gender, firstName,
                    lastName, email,
                    password, phoneNumber);
            return repository.onRegistration(gender, firstName,
                    lastName, email,
                    password, phoneNumber);
        }catch (Throwable throwable){
            return Completable.error(throwable);
        }
    }

    private void validate(int gender, String firstName, String lastName, String email, String password, String phoneNumber) {
        List<String> errors = new ArrayList<>();


        errors.addAll(validateName(firstName, "First"));
        errors.addAll(validateName(lastName, "Last"));
        errors.addAll(validateEmail(email));
        errors.addAll(validatePass(password));
        errors.addAll(validatePhone(phoneNumber));

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


    private List<String> validateName(String name, String type) {
        List<String> errors = new ArrayList<>();
        if (name.isEmpty()){
            errors.add(type + " name is required\n");
        }
        if (name.length() < 2 || name.length() > 50){
            errors.add(type + " name length should be between2 and 50 characters\n");
        }
        if (!name.matches("[a-zA-Z]+")){
            errors.add(type + " name may contain only alphabet characters\n");
        }
        return errors;
    }

    private List<String> validateEmail(String email){
        List<String> errors = new ArrayList<>();
        if (email.isEmpty()){
            errors.add("Email is required\n");
        }
        if (email.length() < 3 || email.length() > 100){
            errors.add("Email length should be between 3 and 100 characters\n");
        }
        if (!email.matches("(?:[a-zA-Z0-9]+)@(?:[a-z0-9]+)\\.(?:[a-z0-9]{2,})")){
            errors.add("Email format is incorrect\n");
        }
        return errors;
    }

    private List<String> validatePass(String pass){
        List<String> errors = new ArrayList<>();
        if (pass.isEmpty()){
            errors.add("Password is required\n");
        }
        if (pass.length() < 8 || pass.length() > 25){
            errors.add("Password length should be between 8 and 25 characters\n");
        }
        return errors;
    }

    private List<String> validatePhone(String phone){
        List<String> errors = new ArrayList<>();
        if (phone.isEmpty()){
            errors.add("Phone is required\n");
        }
        if (!phone.matches("\\+(?:[0-9]+)"))
        {
            errors.add("Phone number may contain only numeric characters, leading by '+'\n");
        }
        return errors;
    }

    private String formatPhone(String phone){
        return phone.replaceAll("(?:[^+0-9])","");
    }
}
