package com.munifahsan.retribusiapp.Register.petugas.view;

public interface PtgRegViewInt {
    void showProgress();
    void hideProgress();
    void showMessage(String msg);
    void setTokenError(String tokenError);
    void setNamaError(String nama);
    void setEmailError(String emailError);
    void setAlamatError(String alamat);
    void setLokasiError(String lokasi);
    void setNohpError(String nohp);
    void setPassError(String passError);
    void setConfirmError(String confirmError);
    void setInputsEnabled(Boolean enabled);
    void navigateToPedagang();
    void navigateToPetugas();
}
