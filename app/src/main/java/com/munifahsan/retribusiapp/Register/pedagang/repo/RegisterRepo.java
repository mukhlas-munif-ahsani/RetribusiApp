package com.munifahsan.retribusiapp.Register.pedagang.repo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.Register.RegisterEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterRepo implements RegisterRepoInt {

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    public String mNowTime;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public RegisterRepo(){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void doSignUp(final String nama, final String email, final String alamat, final String lokasi, final String nohp, final String level, final String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    input(nama, email, alamat, lokasi, nohp, level, pass);
                } else {
                    String error = task.getException().getMessage();
                    postEvent(RegisterEvent.onSignUpError, error);
                }
            }
        });
    }

    public void input(final String nama, final String email, final String alamat, final String lokasi, final String nohp, final String level, String pass){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                getNowTime();

                String token_id = task.getResult().getToken();

                String user_id = auth.getCurrentUser().getUid();

                Map<String, Object> userMap = new HashMap<>();
                userMap.put("nama", nama);
                userMap.put("email", email);
                userMap.put("alamat", alamat);
                userMap.put("lokasi", lokasi);
                userMap.put("nohp_number", nohp);
                userMap.put("level", level);
                userMap.put("token_id", token_id);
                userMap.put("created_at", mNowTime);

                Map<String, Object> INVMap = new HashMap<>();
                INVMap.put("saldo", 0);
                INVMap.put("created_at", mNowTime);
                INVMap.put("updated_at", mNowTime);

                firestore.collection("USERS").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            firestore.collection("USERS").document(user_id).collection("INV").document("SALDO")
                                    .set(INVMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        postEvent(RegisterEvent.onSignUpSuccess, null, level);
                                    } else {
                                        String error = task.getException().getMessage();
                                        postEvent(RegisterEvent.onSignUpError, error, null);
                                    }
                                }
                            });
                        } else {
                            String error = task.getException().getMessage();
                            postEvent(RegisterEvent.onSignUpError, error, null);
                        }
                    }
                });
            }
        });
    }

    public void getNowTime() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, d MMM, yyyy");
        mNowTime = dateFormat.format(calendar.getTime());
    }

    public void postEvent(int type, String errorMessage, String level){
        RegisterEvent event = new RegisterEvent();

        event.setEventType(type);

        if (level != null){
            event.setLevel(level);
        }

        if (errorMessage != null){
            event.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }

    private void postEvent(int type) {
        postEvent(type, null, null);
    }

    private void postEvent(int type, String level) {
        postEvent(type, null, level);
    }

}
