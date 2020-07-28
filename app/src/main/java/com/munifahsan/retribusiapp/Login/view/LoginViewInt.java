package com.munifahsan.retribusiapp.Login.view;

public interface LoginViewInt {

    void showMessage (String msg);
    void showProgress();
    void hideProgress();
    void setEmailError(String emailError);
    void setPassError(String passError);
    void navitageToAdmin();
    void navigateToPedagang();
    void navigateToPetugas();
    void navigateToRegister();
    void setInputs(boolean enabeled);
}
