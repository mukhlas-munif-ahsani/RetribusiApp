package com.munifahsan.retribusiapp.Login.repo;

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
import com.munifahsan.retribusiapp.Login.LoginEvent;

import java.util.HashMap;
import java.util.Map;

public class LoginRepo implements LoginRepoInt{

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public LoginRepo(){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }


    @Override
    public void Login(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            String token_id = task.getResult().getToken();
                            String current_id = auth.getCurrentUser().getUid();

                            Map<String, Object> tokenMap = new HashMap<>();
                            tokenMap.put("token_id", token_id);

                            firestore.collection("USERS").document(current_id).update(tokenMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    postEvent(LoginEvent.onLoginSuccess);
                                }
                            });
                        }
                    });
                } else {
                    String error = task.getException().getMessage();
                    postEvent(LoginEvent.onLoginError, error);
                }
            }
        });
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }
}
