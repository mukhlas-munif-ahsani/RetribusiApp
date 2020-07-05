package com.munifahsan.retribusiapp.HomePedagang.repo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent;
import com.munifahsan.retribusiapp.Register.RegisterEvent;

public class HomePedagangRepo implements HomePedagangRepoInt{

    private FirebaseAuth mAuth;
    private String current_id;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firebaseFirestore.collection("USERS");

    public HomePedagangRepo() {
        mAuth = FirebaseAuth.getInstance();
        current_id = mAuth.getCurrentUser().getUid();
    }

    @Override
    public void getData() {
        collectionReference.document(current_id).collection("INV").document("SALDO")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HomePedagangModel homePedagangModel = documentSnapshot.toObject(HomePedagangModel.class);
                int saldo = homePedagangModel.getSaldo();
                String created_at = homePedagangModel.getCreated_at();
                String updated_at = homePedagangModel.getUpdated_at();

                postEvent(HomePedagangEvent.onGetDataSuccess, saldo, current_id, created_at, updated_at);
                Log.v("Home Pedagang Repo", saldo + " " + current_id + " " + updated_at);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                postEvent(HomePedagangEvent.onGetDataError, e.getMessage());

            }
        });
    }

    public void postEvent(int type, String errorMessage, Integer saldo, String user_id, String created_at, String updated_at){
        HomePedagangEvent event = new HomePedagangEvent();

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
