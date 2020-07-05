package com.munifahsan.retribusiapp.HomePedagang.view;

public interface HomePedagangViewInt {
    void loadQrCode(String data);
    void setSaldo(int saldo);
    void showQr();
    void hideQr();
    void showSaldo();
    void hideSaldo();
    void showSaldoLoading();
    void hideSaldoLoading();
    void showQrLoading();
    void hideQrLoading();
    void showMessage(String msg);
}
