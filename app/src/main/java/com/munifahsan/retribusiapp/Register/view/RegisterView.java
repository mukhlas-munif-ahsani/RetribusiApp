package com.munifahsan.retribusiapp.Register.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.munifahsan.retribusiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterView extends AppCompatActivity implements RegisterViewInt {

    private ProgressBar progressBar;
    private TextInputEditText nama;
    private TextInputEditText alamat;
    private TextInputEditText nohp;
    private AutoCompleteTextView level;
    private TextInputEditText pass;
    private TextInputEditText confirmPass;
    private Button register;

    @BindView(R.id.namaTextInputLayout)
    TextInputLayout namaLay;
    @BindView(R.id.alamatTextInputLayout)
    TextInputLayout alamatLay;
    @BindView(R.id.nohpTextInputLayout)
    TextInputLayout nohpLay;
    @BindView(R.id.levelTextInputLayout)
    TextInputLayout levelLay;
    @BindView(R.id.passTextInputLayout)
    TextInputLayout passLay;
    @BindView(R.id.confirmTextInputLayout)
    TextInputLayout confirmLay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        progressBar = findViewById(R.id.registerProgress);
        nama = findViewById(R.id.namaTxt);
        alamat = findViewById(R.id.alamatTxt);
        nohp = findViewById(R.id.nohpTxt);
        level = findViewById(R.id.levelTxt);
        pass = findViewById(R.id.passTxt);
        confirmPass = findViewById(R.id.confirmPassTxt);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setNamaError(String nama) {
        namaLay.setError(nama);
    }

    @Override
    public void setAlamatError(String alamat) {
        alamatLay.setError(alamat);
    }

    @Override
    public void setNohpError(String nohp) {
        nohpLay.setError(nohp);
    }

    @Override
    public void setLevelError(String level) {
        levelLay.setError(level);
    }

    @Override
    public void setPassError(String passError) {
        passLay.setError(passError);
    }

    @Override
    public void setConfirmError(String confirmError) {
        confirmLay.setError(confirmError);
    }

    @Override
    public void setInputsEnabled(Boolean enabled) {
        nama.setEnabled(enabled);
        alamat.setEnabled(enabled);
        nohp.setEnabled(enabled);
        level.setEnabled(enabled);
        pass.setEnabled(enabled);
        confirmPass.setEnabled(enabled);
    }

    @Override
    public void sendToMainAct() {

    }

    @OnClick(R.id.registerBtn)
    public void Onclick() {

        String nama = this.nama.getText().toString();
        String alamat  = this.alamat.getText().toString();
        String nohp = this.nohp.getText().toString();
        String level = this.level.getText().toString();
        String pass = this.pass.getText().toString();
        String confirm = confirmPass.getText().toString();


    }
}
