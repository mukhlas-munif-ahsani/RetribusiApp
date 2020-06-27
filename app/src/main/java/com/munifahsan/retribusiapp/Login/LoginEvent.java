package com.munifahsan.retribusiapp.Login;

public class LoginEvent {
    public static final int onLoginError = 0;
    public static final int onLoginSuccess = 1;

    private int eventType;
    private String errorMessage;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
