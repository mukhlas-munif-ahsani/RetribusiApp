package com.munifahsan.retribusiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.munifahsan.retribusiapp.Login.view.LoginView;
import com.munifahsan.retribusiapp.MainAdmin.MainAdminActivity;
import com.munifahsan.retribusiapp.MainPedagang.MainPedagang;
import com.munifahsan.retribusiapp.MainPetugas.MainPetugas;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private String current_id;

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
            navigateToLogin();
        }
        else {
//            sendToMain();

            current_id = mAuth.getCurrentUser().getUid();

            firebaseFirestore.collection("USERS").document(current_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        String level = task.getResult().getString("level");

                        if (level.equals("PETUGAS")) {

                            navigateToPetugas();

                        }

                        if (level.equals("PEDAGANG")) {

                            navigateToPedagang();

                        }

                        if (level.equals("ADMIN")){
                            navitageToAdmin();
                        }
                    } else {

                        String errorMessage = task.getException().getMessage();
                        showMessage(errorMessage);
                    }

                }
            });

        }
    }

    private void navitageToAdmin() {
        Intent intent = new Intent(this, MainAdminActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToPetugas() {
        Intent intent = new Intent(this, MainPetugas.class);
        startActivity(intent);
        finish();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
        finish();
    }

    private void navigateToPedagang() {
        Intent intent = new Intent(this, MainPedagang.class);
        startActivity(intent);
        finish();
    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
