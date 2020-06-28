package com.munifahsan.retribusiapp.Register.presenter;

import com.munifahsan.retribusiapp.BasePressInt;

public interface RegisterPresInt extends BasePressInt {
    boolean isValidForm(String nama, String email, String alamat, String nohp, String level, String pass, String confirm);
    void validateRegister(String nama, String email, String alamat, String nohp, String level, String pass);
}
