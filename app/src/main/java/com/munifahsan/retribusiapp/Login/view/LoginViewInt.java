package com.munifahsan.retribusiapp.Login.view;

public interface LoginViewInt {

    void showMessage (String msg);
    void showProgress();
    void hideProgress();
    void setEmailError(String emailError);
    void setPassError(String passError);
    void navigateToMain();
    void navigateToRegister();
    void setInputs(boolean enabeled);
}
