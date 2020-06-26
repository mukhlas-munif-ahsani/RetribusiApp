package com.munifahsan.retribusiapp.Register.repo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.StorageReference;
import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.Register.RegisterEvent;

import java.util.HashMap;
import java.util.Map;

public class RegisterRepo implements RegisterRepoInt {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public RegisterRepo(){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }


    @Override
    public void doSignUp(final String nama, final String email, final String alamat, final String nohp, final String level, final String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    input(nama, email, alamat, nohp, level, pass);
                } else {
                    String error = task.getException().getMessage();
                    postEvent(RegisterEvent.onSignUpError, error);
                }
            }
        });
    }

    public void input(final String nama, final String email, final String alamat, final String nohp, final String level, String pass){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token_id = task.getResult().getToken();

                String user_id = auth.getCurrentUser().getUid();

                Map<String, String> userMap = new HashMap<>();
                userMap.put("nama", nama);
                userMap.put("email", email);
                userMap.put("alamat", alamat);
                userMap.put("nohp number", nohp);
                userMap.put("level", level);
                userMap.put("token_id", token_id);

                firestore.collection("USERS").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            postEvent(RegisterEvent.onSignUpSuccess);
                        } else {
                            String error = task.getException().getMessage();
                            postEvent(RegisterEvent.onSignUpError, error);
                        }
                    }
                });
            }
        });
    }

    public void postEvent(int type, String errorMessage){
        RegisterEvent event = new RegisterEvent();

        event.setEventType(type);

        if (errorMessage != null){
            event.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }

}
