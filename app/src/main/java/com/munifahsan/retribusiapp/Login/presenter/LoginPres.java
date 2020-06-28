package com.munifahsan.retribusiapp.Login.presenter;

import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.Login.LoginEvent;
import com.munifahsan.retribusiapp.Login.repo.LoginRepo;
import com.munifahsan.retribusiapp.Login.repo.LoginRepoInt;
import com.munifahsan.retribusiapp.Login.view.LoginViewInt;

import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.munifahsan.retribusiapp.Login.LoginEvent.onLoginError;
import static com.munifahsan.retribusiapp.Login.LoginEvent.onLoginSuccess;

public class LoginPres implements LoginPresInt{

    private LoginViewInt loginViewInt;
    private EventBus eventBus;
    private LoginRepoInt loginRepoInt;

    public LoginPres(LoginViewInt loginViewInt) {
        this.loginViewInt = loginViewInt;
        loginRepoInt = new LoginRepo();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginViewInt = null;
        eventBus.unregister(this);
    }

    @Subscribe
    public void onEventMainThread(LoginEvent event){
        switch (event.getEventType()){
            case onLoginSuccess:
                onLoginSuccess(event.getLevel());
                break;
            case onLoginError:
                onLoginError(event.getErrorMessage());
                break;
        }
    }

    private void onLoginError(String error) {
        loginViewInt.showMessage(error);
    }

    private void onLoginSuccess(String level) {
        if (level.equals("PEDAGANG")){
            loginViewInt.navigateToPedagang();
        }

        if (level.equals("PETUGAS")){
            loginViewInt.navigateToPetugas();
        }
    }

    public boolean isValidForm(String email, String pass){
        boolean isValid = true;
        if (email.isEmpty()){
            isValid = false;
            loginViewInt.setEmailError("E-mail Tidak Boleh Kosong");
        }

        if (!email.isEmpty() && !isEmailValid(email)){
            isValid = false;
            loginViewInt.setEmailError("E-mail Tidak Valid");
        }

        if (pass.isEmpty()){
            isValid = false;
            loginViewInt.setPassError("Password Tidak Boleh Kosong");
        }
        return isValid;
    }

    public void validateLogin(String email, String pass){
        loginViewInt.showProgress();
        loginViewInt.setInputs(false);

        loginRepoInt.Login(email, pass);
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
