package com.munifahsan.retribusiapp.Register;

public class RegisterModel {
    private boolean token;

    public RegisterModel() {
    }

    public RegisterModel(boolean token) {
        this.token = token;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }
}
