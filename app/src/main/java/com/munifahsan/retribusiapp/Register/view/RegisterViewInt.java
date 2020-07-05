package com.munifahsan.retribusiapp.Register.view;

public interface RegisterViewInt {
    void showProgress();
    void hideProgress();
    void showMessage(String msg);
    void setNamaError(String nama);
    void setEmailError(String emailError);
    void setAlamatError(String alamat);
    void setLokasiError(String lokasi);
    void setNohpError(String nohp);
    void setLevelError(String level);
    void setPassError(String passError);
    void setConfirmError(String confirmError);
    void setInputsEnabled(Boolean enabled);
    void navigateToPedagang();
    void navigateToPetugas();

}
