package com.munifahsan.retribusiapp.Login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.munifahsan.retribusiapp.MainActivity;
import com.munifahsan.retribusiapp.R;
import com.munifahsan.retribusiapp.Register.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends AppCompatActivity implements LoginViewInt{

    private LoginViewInt loginViewInt;

    @BindView(R.id.loginProgress)
    ProgressBar loginProgress;
    @BindView(R.id.emailTxt)
    TextInputEditText email;
    @BindView(R.id.passTxt)
    TextInputEditText pass;

    @BindView(R.id.emailTextInputLayout)
    TextInputLayout emailLay;
    @BindView(R.id.passTextInputLayout)
    TextInputLayout passlay;

    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.login_register)
    Button logReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        loginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loginProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setEmailError(String emailError) {
        emailLay.setError(emailError);
    }

    @Override
    public void setPassError(String passError) {
        passlay.setError(passError);
    }

    @OnClick(R.id.loginBtn)
    public void loginOnClick(){
        String email = this.email.getText().toString();
        String pass = this.pass.getText().toString();


    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    @OnClick(R.id.login_register)
    public void navigateToRegister() {
        Intent intent = new Intent(this, RegisterView.class);
        startActivity(intent);
    }
}
