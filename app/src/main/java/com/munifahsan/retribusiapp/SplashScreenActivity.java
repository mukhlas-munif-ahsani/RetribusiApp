package com.munifahsan.retribusiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.munifahsan.retribusiapp.Register.view.RegisterView;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            senToRegister();
        }
//        else {
//
//            current_user_id = mAuth.getCurrentUser().getUid();
//
//            firebaseFirestore.collection("USERS").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                    if (task.isSuccessful()) {
//
//                        if (!task.getResult().exists()) {
//
//                            sendToSetup();
//
//                        }
//
//                        if (task.getResult().exists()) {
//
//                            sendToMain();
//
//                        }
//
//                    } else {
//
//                        String errorMessage = task.getException().getMessage();
//                        showMessage(errorMessage);
//                    }
//
//                }
//            });
//
//        }
    }

    private void senToRegister() {
        Intent intent = new Intent(this, RegisterView.class);
        startActivity(intent);
        finish();
    }
}
