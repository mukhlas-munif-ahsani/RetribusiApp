package com.munifahsan.retribusiapp.Register.petugas.pres;

import com.munifahsan.retribusiapp.BasePressInt;

public interface PtgRegPresInt extends BasePressInt {
    boolean isValidForm(String token, String nama, String email, String alamat, String loksai, String nohp,  String pass, String confirm);
    void validateRegister(String token, String nama, String email, String alamat, String lokasi, String nohp, String pass);
}
