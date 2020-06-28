package com.munifahsan.retribusiapp.Login.presenter;

import com.munifahsan.retribusiapp.BasePressInt;

public interface LoginPresInt extends BasePressInt {
    boolean isValidForm(String email, String pass);
    void validateLogin(String email, String pass);
}
