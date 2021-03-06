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
import com.munifahsan.retribusiapp.Login.presenter.LoginPres;
import com.munifahsan.retribusiapp.Login.presenter.LoginPresInt;
import com.munifahsan.retribusiapp.MainAdmin.MainAdminActivity;
import com.munifahsan.retribusiapp.MainPedagang.MainPedagang;
import com.munifahsan.retribusiapp.MainPetugas.MainPetugas;
import com.munifahsan.retribusiapp.R;
import com.munifahsan.retribusiapp.Register.RegisterActivity;
import com.munifahsan.retribusiapp.Register.pedagang.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends AppCompatActivity implements LoginViewInt{

    private LoginPresInt loginPresInt;

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
        loginPresInt = new LoginPres(this);
        loginPresInt.onCreate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresInt.onDestroy();
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

        if (loginPresInt.isValidForm(email, pass)){
            loginPresInt.validateLogin(email, pass);
        }
    }

    @Override
    public void navitageToAdmin() {
        Intent intent = new Intent(this, MainAdminActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToPedagang() {
        Intent intent = new Intent(this, MainPedagang.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToPetugas() {
        Intent intent = new Intent(this, MainPetugas.class);
        startActivity(intent);
        finish();
    }

    @Override
    @OnClick(R.id.login_register)
    public void navigateToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void setInputs(boolean enabeled) {
        email.setEnabled(enabeled);
        pass.setEnabled(enabeled);
    }
}
