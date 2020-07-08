package com.munifahsan.retribusiapp.Register.petugas.pres;

import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.Register.RegisterEvent;
import com.munifahsan.retribusiapp.Register.pedagang.repo.RegisterRepoInt;
import com.munifahsan.retribusiapp.Register.pedagang.view.RegisterViewInt;
import com.munifahsan.retribusiapp.Register.petugas.repo.PtgRegRepo;
import com.munifahsan.retribusiapp.Register.petugas.repo.PtgRegRepoInt;
import com.munifahsan.retribusiapp.Register.petugas.view.PtgRegViewInt;

import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.munifahsan.retribusiapp.Register.RegisterEvent.onSignUpError;
import static com.munifahsan.retribusiapp.Register.RegisterEvent.onSignUpSuccess;

public class PtgRegPres implements PtgRegPresInt {
    private PtgRegViewInt ptgRegViewInt;
    private EventBus eventBus;
    private PtgRegRepoInt ptgRegRepoInt;

    public PtgRegPres(PtgRegViewInt ptgRegViewInt) {
        this.ptgRegViewInt = ptgRegViewInt;
        ptgRegRepoInt = new PtgRegRepo();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        ptgRegViewInt = null;
        eventBus.unregister(this);
    }

    @Subscribe
    public void onEventMainThread(RegisterEvent event){
        switch (event.getEventType()){
            case onSignUpSuccess:
                onSignUpSuccess(event.getLevel());
                break;
            case onSignUpError:
                onSignUpError(event.getErrorMessage(), event.getTokenError());
                break;
        }
    }

    private void onSignUpError(String errorMessage, String errorToken) {
        if (errorMessage != null){
            ptgRegViewInt.showMessage(errorMessage);
            ptgRegViewInt.setInputsEnabled(true);
            ptgRegViewInt.hideProgress();
        }

        if (errorToken != null){
            ptgRegViewInt.setTokenError(errorToken);
            ptgRegViewInt.setInputsEnabled(true);
            ptgRegViewInt.hideProgress();
        }
    }

    private void onSignUpSuccess(String level) {

        if (level.equals("PEDAGANG")){
            ptgRegViewInt.navigateToPedagang();
        }

        if (level.equals("PETUGAS")){
            ptgRegViewInt.navigateToPetugas();
        }
    }

    public boolean isValidForm(String token, String nama, String email, String alamat, String lokasi, String nohp, String pass, String confirm){
        boolean isValid = true;
        if (token.isEmpty()){
            isValid = false;
            ptgRegViewInt.setTokenError("Nomor Tidak Boleh Kosong");
        }

        if (nama.isEmpty()){
            isValid = false;
            ptgRegViewInt.setNamaError("Nama Tidak Boleh Kosong");
        }

        if (email.isEmpty()){
            isValid = false;
            ptgRegViewInt.setEmailError("E-mail Tidak Boleh Kosong");
        }

        if (!email.isEmpty() && !isEmailValid(email)){
            isValid = false;
            ptgRegViewInt.setEmailError("E-mail Tidak Valid");
        }

        if (alamat.isEmpty()){
            isValid = false;
            ptgRegViewInt.setAlamatError("Alamat Tidak Boleh Kosong");
        }

        if (lokasi.isEmpty()){
            isValid = false;
            ptgRegViewInt.setLokasiError("Lokasi Tidak Boleh Kosong");
        }

        if (nohp.isEmpty()){
            isValid = false;
            ptgRegViewInt.setNohpError("No Hp Tidak Boleh Kosong");
        }

        if (pass.isEmpty()){
            isValid = false;
            ptgRegViewInt.setPassError("Password Tidak Boleh Kosong");
        }

        if (confirm.isEmpty()){
            isValid = false;
            ptgRegViewInt.setConfirmError("Confirm Password Tidak Boleh Kosong");
        }

        if (!pass.isEmpty() && !confirm.isEmpty() && !pass.equals(confirm)){
            isValid = false;
            ptgRegViewInt.showMessage("Confirm Password dan Password Tidak Sama");
        }
        return isValid;
    }

    public void validateRegister(String token, String nama, String email, String alamat, String lokasi, String nohp, String pass){
        ptgRegViewInt.setInputsEnabled(false);
        ptgRegViewInt.showProgress();

        ptgRegRepoInt.checkToken(token, nama, email, alamat, lokasi, nohp, "PETUGAS",  pass);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
