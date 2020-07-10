package com.munifahsan.retribusiapp.HomePetugas.repo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.HomePetugas.HomePtgEvent;

public class HomePtgRepo implements HomePtgRepoInt {

    private FirebaseAuth mAuth;
    private String current_id;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firebaseFirestore.collection("USERS");

    public HomePtgRepo() {
        mAuth = FirebaseAuth.getInstance();
        current_id = mAuth.getCurrentUser().getUid();
    }

    @Override
    public void getData() {
        collectionReference.document(current_id).collection("INV").document("SALDO")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HomePtgModel homePtgModel = documentSnapshot.toObject(HomePtgModel.class);
                int saldo = homePtgModel.getSaldo();
                String created_at = homePtgModel.getCreated_at();
                String updated_at = homePtgModel.getUpdated_at();

                postEvent(HomePtgEvent.onGetDataSuccess, saldo, current_id, created_at, updated_at);
                Log.v("Home Pedagang Repo", saldo + " " + current_id + " " + updated_at);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                postEvent(HomePtgEvent.onGetDataError, e.getMessage());

            }
        });
    }

    public void postEvent(int type, String errorMessage, Integer saldo, String user_id, String created_at, String updated_at){
        HomePtgEvent event = new HomePtgEvent();

        event.setEventType(type);

        if (errorMessage != null){
            event.setErrorMessage(errorMessage);
        }

        event.setUser_id(user_id);
        event.setSaldo(saldo);
        event.setCreated_at(created_at);
        event.setUpdated_at(updated_at);

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }

    private void postEvent(int type, Integer saldo, String user_id, String created_at, String updated_at){
        postEvent(type, null, saldo, user_id, created_at, updated_at);
    }

    private void postEvent(int type, String errorMessage) {
        postEvent(type, errorMessage, null, null, null, null);
    }
}
