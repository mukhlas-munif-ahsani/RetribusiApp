package com.munifahsan.retribusiapp.Login.repo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.Login.LoginEvent;

import java.util.HashMap;
import java.util.Map;

public class LoginRepo implements LoginRepoInt {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String user_id;
    private FirebaseUser currentUser;
    private String level;

    public LoginRepo() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public void Login(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user_id = auth.getCurrentUser().getUid();
                    firestore.collection("USERS").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        //            public String level;
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                level = task.getResult().getString("level");

                                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        String token_id = task.getResult().getToken();

                                        Map<String, Object> tokenMap = new HashMap<>();
                                        tokenMap.put("token_id", token_id);

                                        firestore.collection("USERS").document(user_id).update(tokenMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                postEvent(LoginEvent.onLoginSuccess, null, level);
                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });

                } else {
                    String error = task.getException().getMessage();
                    postEvent(LoginEvent.onLoginError, error, null);
                }
            }
        });
    }

    public String getUserLevel() {
//        String level = null;
        firestore.collection("USERS").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            //            public String level;
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    level = task.getResult().getString("level");

                }
            }
        });

        return level;
    }

    private void postEvent(int type, String errorMessage, String level) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);

        if (level != null){
            loginEvent.setLevel(level);
        }

        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null, null);
    }

    private void postEvent(int type, String level) {
        postEvent(type, null, level);
    }
}
