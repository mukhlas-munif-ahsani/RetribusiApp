package com.munifahsan.retribusiapp.Profile.repo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.Profile.ProfileEvent;

public class ProfileRepo  implements ProfileRepoInt {

    private FirebaseAuth mAuth;
    private String current_id;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firebaseFirestore.collection("USERS");

    public ProfileRepo(){
        mAuth = FirebaseAuth.getInstance();
        current_id = mAuth.getCurrentUser().getUid();
    }

    @Override
    public void getData() {
        collectionReference.document(current_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ProfileModel model = documentSnapshot.toObject(ProfileModel.class);
                String nama = model.getNama();
                String email = model.getEmail();
                String nohp = model.getNohp_number();
                String alamat = model.getAlamat();
                String lokasi = model.getLokasi();

                postEvent(ProfileEvent.onGetDataSuccess, nama, email, nohp, alamat, lokasi);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ProfileEvent.onGetDataError, e.getMessage());
            }
        });
    }

    public void postEvent(int type, String errorMessage, String nama, String email, String nohp, String alamat, String lokasi){
        ProfileEvent event = new ProfileEvent();

        event.setEventType(type);

        if (errorMessage != null){
            event.setErrorMessage(errorMessage);
        }

        event.setNama(nama);
        event.setEmail(email);
        event.setNohp_number(nohp);
        event.setAlamat(alamat);
        event.setLokasi(lokasi);

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }

    private void postEvent(int type, String nama, String email, String nohp, String alamat, String lokasi){
        postEvent(type, null, nama, email, nohp, alamat, lokasi);
    }

    private void postEvent(int type, String errorMessage) {
        postEvent(type, errorMessage, null, null, null, null);
    }
}
