package com.munifahsan.retribusiapp.Register.presenter;

import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.Register.RegisterEvent;
import com.munifahsan.retribusiapp.Register.repo.RegisterRepo;
import com.munifahsan.retribusiapp.Register.repo.RegisterRepoInt;
import com.munifahsan.retribusiapp.Register.view.RegisterViewInt;

import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.munifahsan.retribusiapp.Register.RegisterEvent.onSignUpError;
import static com.munifahsan.retribusiapp.Register.RegisterEvent.onSignUpSuccess;

public class RegisterPress implements RegisterPressInt {

    private RegisterViewInt registerViewInt;
    private EventBus eventBus;
    private RegisterRepoInt registerRepoInt;

    public RegisterPress(RegisterViewInt registerViewInt){
        this.registerViewInt = registerViewInt;
        registerRepoInt = new RegisterRepo();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        registerViewInt = null;
        eventBus.unregister(this);
    }

    @Subscribe
    public void onEventMainThread(RegisterEvent event){
        switch (event.getEventType()){
            case onSignUpSuccess:
                onSignUpSuccess(event.getLevel());
                break;
            case onSignUpError:
                onSignUpError();
                break;
        }
    }

    private void onSignUpError() {

    }

    private void onSignUpSuccess(String level) {

        if (level.equals("PEDAGANG")){
            registerViewInt.navigateToPedagang();
        }

        if (level.equals("PETUGAS")){
            registerViewInt.navigateToPetugas();
        }    }

    public boolean isValidForm(String nama, String email, String alamat, String nohp, String level, String pass, String confirm){
        boolean isValid = true;
        if (nama.isEmpty()){
            isValid = false;
            registerViewInt.setNamaError("Nama Tidak Boleh Kosong");
        }

        if (email.isEmpty()){
            isValid = false;
            registerViewInt.setEmailError("E-mail Tidak Boleh Kosong");
        }

        if (!email.isEmpty() && !isEmailValid(email)){
            isValid = false;
            registerViewInt.setEmailError("E-mail Tidak Valid");
        }

        if (alamat.isEmpty()){
            isValid = false;
            registerViewInt.setAlamatError("Alamat Tidak Boleh Kosong");
        }

        if (nohp.isEmpty()){
            isValid = false;
            registerViewInt.setNohpError("No Hp Tidak Boleh Kosong");
        }

        if (level.isEmpty()){
            isValid = false;
            registerViewInt.setLevelError("Anda Harus Memilih 1");
        }

        if (pass.isEmpty()){
            isValid = false;
            registerViewInt.setPassError("Password Tidak Boleh Kosong");
        }

        if (confirm.isEmpty()){
            isValid = false;
            registerViewInt.setConfirmError("Confirm Password Tidak Boleh Kosong");
        }

        if (!pass.isEmpty() && !confirm.isEmpty() && !pass.equals(confirm)){
            isValid = false;
            registerViewInt.showMessage("Confirm Password dan Password Tidak Sama");
        }
        return isValid;
    }

    public void validateRegister(String nama, String email, String alamat, String nohp, String level, String pass){
        registerViewInt.setInputsEnabled(false);
        registerViewInt.showProgress();

        registerRepoInt.doSignUp(nama, email, alamat, nohp, level, pass);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
