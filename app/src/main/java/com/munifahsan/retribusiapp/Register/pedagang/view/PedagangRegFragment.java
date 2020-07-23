package com.munifahsan.retribusiapp.Register.pedagang.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.munifahsan.retribusiapp.Login.view.LoginView;
import com.munifahsan.retribusiapp.MainPedagang.MainPedagang;
import com.munifahsan.retribusiapp.MainPetugas.MainPetugas;
import com.munifahsan.retribusiapp.R;
import com.munifahsan.retribusiapp.Register.pedagang.presenter.RegisterPres;
import com.munifahsan.retribusiapp.Register.pedagang.presenter.RegisterPresInt;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedagangRegFragment extends Fragment implements RegisterViewInt{

    private ProgressBar progressBar;
    private TextInputEditText nama;
    private TextInputEditText alamat;
    private TextInputEditText nohp;
    private TextInputEditText pass;
    private TextInputEditText confirmPass;
    private Button register;

    @BindView(R.id.emailTxt)
    TextInputEditText email;
    @BindView(R.id.namaTextInputLayout)
    TextInputLayout namaLay;
    @BindView(R.id.emailTextInputLayout)
    TextInputLayout emailLay;
    @BindView(R.id.alamatTextInputLayout)
    TextInputLayout alamatLay;
    @BindView(R.id.lokasiTextInputLayout)
    TextInputLayout lokasiLay;
    @BindView(R.id.lokasiTxt)
    TextInputEditText lokasi;
    @BindView(R.id.nohpTextInputLayout)
    TextInputLayout nohpLay;
    @BindView(R.id.passTextInputLayout)
    TextInputLayout passLay;
    @BindView(R.id.confirmTextInputLayout)
    TextInputLayout confirmLay;

    @BindView(R.id.register_login)
    Button regLogin;

    private RegisterPresInt registerPressInt;

    public PedagangRegFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedagang_reg, container, false);

        ButterKnife.bind(this, view);

        registerPressInt = new RegisterPres(this);
        registerPressInt.onCreate();

        progressBar = view.findViewById(R.id.registerProgress);
        nama = view.findViewById(R.id.nominalTxt);
        alamat = view.findViewById(R.id.alamatTxt);
        nohp = view.findViewById(R.id.nohpTxt);
        pass = view.findViewById(R.id.passTxt);
        confirmPass = view.findViewById(R.id.confirmPassTxt);

        return view;
    }

    @Override
    public void onDestroy() {
        registerPressInt.onDestroy();
        super.onDestroy();
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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setNamaError(String nama) {
        namaLay.setError(nama);
    }

    @Override
    public void setEmailError(String emailError) {
        emailLay.setError(emailError);
    }

    @Override
    public void setAlamatError(String alamat) {
        alamatLay.setError(alamat);
    }

    @Override
    public void setLokasiError(String lokasi) {
        lokasiLay.setError(lokasi);
    }

    @Override
    public void setNohpError(String nohp) {
        nohpLay.setError(nohp);
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
        lokasi.setEnabled(enabled);
        nohp.setEnabled(enabled);
        pass.setEnabled(enabled);
        confirmPass.setEnabled(enabled);
    }

    @Override
    public void navigateToPedagang() {
        Intent intent = new Intent(getActivity(), MainPedagang.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void navigateToPetugas() {
        Intent intent = new Intent(getActivity(), MainPetugas.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.registerBtn)
    public void regOnclick() {

        String nama = this.nama.getText().toString();
        String email = this.email.getText().toString();
        String alamat  = this.alamat.getText().toString();
        String lokasi = this.lokasi.getText().toString();
        String nohp = this.nohp.getText().toString();
        String pass = this.pass.getText().toString();
        String confirm = confirmPass.getText().toString();

        if (registerPressInt.isValidForm(nama, email, alamat, lokasi, nohp, pass, confirm)){
            registerPressInt.validateRegister(nama, email, alamat, lokasi, nohp, pass);
        }
    }

    @OnClick(R.id.register_login)
    public void regLogOnClick(){
        Intent intent = new Intent(getActivity(), LoginView.class);
        startActivity(intent);
    }
}
