package com.munifahsan.retribusiapp.Register.petugas.repo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.Register.RegisterEvent;
import com.munifahsan.retribusiapp.Register.RegisterModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PtgRegRepo implements PtgRegRepoInt{

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    public String mNowTime;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public PtgRegRepo() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void doSignUp(final String token, final String nama, final String email, final String alamat, final String lokasi, final String nohp, final String level, final String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    input(token, nama, email, alamat, lokasi, nohp, level, pass);
                } else {
                    String error = task.getException().getMessage();
                    postEvent(RegisterEvent.onSignUpError, error);
                }
            }
        });
    }

    public void input(final String token, final String nama, final String email, final String alamat, final String lokasi, final String nohp, final String level, String pass){
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
                                        postEvent(RegisterEvent.onSignUpSuccess, null, level, null);
//                                        checkToken(token, nama, email, alamat, lokasi, nohp, level, pass);
                                    } else {
                                        String error = task.getException().getMessage();
                                        postEvent(RegisterEvent.onSignUpError, error, null, null);
                                    }
                                }
                            });
                        } else {
                            String error = task.getException().getMessage();
                            postEvent(RegisterEvent.onSignUpError, error, null, null);
                        }
                    }
                });
            }
        });
    }

    public void checkToken(final String token, final String nama, final String email, final String alamat, final String lokasi, final String nohp, final String level, String pass){
        firestore.collection("TOKEN").document(token).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    RegisterModel model = documentSnapshot.toObject(RegisterModel.class);

                    if (!model.isToken()){
                        firestore.collection("TOKEN").document(token).update("token", true).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    doSignUp(token, nama, email, alamat, lokasi, nohp, level, pass);
                                } else {
                                    postEvent(RegisterEvent.onSignUpError, task.getException().getMessage());
                                }
                            }
                        });
//                        model.setToken(true);
//                        postEvent(RegisterEvent.onSignUpSuccess, null, level, null);
                    } else {
                        postEvent(RegisterEvent.onSignUpError, null, null, "Nomor ini telah digunakan");
                    }

                } else {
                    postEvent(RegisterEvent.onSignUpError, null, null, "Nomor ini tidak terdaftar");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(RegisterEvent.onSignUpError, e.getMessage());
            }
        });
    }

    public void getNowTime() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, d MMM, yyyy");
        mNowTime = dateFormat.format(calendar.getTime());
    }

    public void postEvent(int type, String errorMessage, String level, String token){
        RegisterEvent event = new RegisterEvent();

        event.setEventType(type);

        if (level != null){
            event.setLevel(level);
        }

        if (errorMessage != null){
            event.setErrorMessage(errorMessage);
        }

        if (token != null){
            event.setTokenError(token);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }

    private void postEvent(int type) {
        postEvent(type, null, null, null);
    }

    private void postEvent(int type, String level) {
        postEvent(type, level, null, null);
    }

}
