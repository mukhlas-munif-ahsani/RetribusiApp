package com.munifahsan.retribusiapp.ScanAct.repo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.ScanAct.ScanActEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ScanActRepo implements ScanActRepoInt {

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    public String mNowTime;

    private FirebaseAuth mAuth;
    private String current_id;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = firebaseFirestore.collection("USERS");
    private DocumentReference pajakRef = firebaseFirestore.collection("BILL").document("PAJAK");


    public ScanActRepo() {
        mAuth = FirebaseAuth.getInstance();
        current_id = mAuth.getCurrentUser().getUid();
    }

    @Override
    public void proceedTopup(String idPedagang, int nominal) {
        usersRef.document(idPedagang).collection("INV").document("SALDO")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ScanActModel model = documentSnapshot.toObject(ScanActModel.class);
                    int saldoPdg = model.getSaldo();

                    int ttlSaldo = saldoPdg + nominal;
                    updateSaldoPdg2(idPedagang, ttlSaldo, nominal);

                } else {
                    postEvent(ScanActEvent.onGetDataError, "Pedagang degan code Qr ini tidak ditemukan");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ScanActEvent.onGetDataError, e.getMessage());
            }
        });
    }

    @Override
    public void proceedPajak(String qrId) {

        pajakRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ScanActModel model = documentSnapshot.toObject(ScanActModel.class);
                int pajak = model.getPajak_1();
                String nama = model.getNama();

                usersRef.document(qrId).collection("INV").document("SALDO")
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ScanActModel model = documentSnapshot.toObject(ScanActModel.class);
                            int saldoPdg = model.getSaldo();

                            if (saldoPdg < pajak) {
                                postEvent(ScanActEvent.onGetDataError, "Saldo pedagang tidak mencukupi");
                            } else {
                                int cutSaldo = saldoPdg - pajak;
                                updateSaldoPdg1(qrId, cutSaldo);
                            }
                        } else {
                            postEvent(ScanActEvent.onGetDataError, "Pedagang degan code Qr ini tidak ditemukan");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(ScanActEvent.onGetDataError, e.getMessage());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ScanActEvent.onGetDataError, e.getMessage());
            }
        });

    }

    public void getPajakData() {

//        final int[] pajak = new int[1];

        pajakRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ScanActModel model = documentSnapshot.toObject(ScanActModel.class);
                int pajak = model.getPajak_1();
                String nama = model.getNama();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ScanActEvent.onGetDataError, e.getMessage());
            }
        });

//        return pajak[0];
    }

    public String getPajakNama() {

        final String[] nama = new String[1];

        pajakRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ScanActModel model = documentSnapshot.toObject(ScanActModel.class);
                nama[0] = model.getNama();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ScanActEvent.onGetDataError, e.getMessage());
            }
        });

        return nama[0];
    }

    public void updateSaldoPdg1(String idPedagang, int saldo) {
        getNowTime();
        usersRef.document(idPedagang).collection("INV").document("SALDO")
                .update("saldo", saldo,
                        "updated_at", mNowTime).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateSaldoPtg1(idPedagang, saldo);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ScanActEvent.onGetDataError, e.getMessage());
            }
        });
    }

    public void updateSaldoPdg2(String idPedagang, int saldo, int nominal) {
        getNowTime();
        usersRef.document(idPedagang).collection("INV").document("SALDO")
                .update("saldo", saldo,
                        "updated_at", mNowTime).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateSaldoPtg2(idPedagang, saldo, nominal);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ScanActEvent.onGetDataError, e.getMessage());
            }
        });
    }

    public void updateSaldoPtg1(String idPedagang, int saldo) {
        getNowTime();

        pajakRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ScanActModel model = documentSnapshot.toObject(ScanActModel.class);
                int pajak = model.getPajak_1();
                String nama = model.getNama();

                usersRef.document(current_id).collection("INV").document("SALDO")
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ScanActModel model = documentSnapshot.toObject(ScanActModel.class);
                        int saldoPtg = model.getSaldo();
                        int plusSaldo = saldoPtg + pajak;

                        usersRef.document(current_id).collection("INV").document("SALDO")
                                .update("saldo", plusSaldo,
                                        "updated_at", mNowTime).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    addHistory(idPedagang, saldo, pajak, nama);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                postEvent(ScanActEvent.onGetDataError, e.getMessage());
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(ScanActEvent.onGetDataError, e.getMessage());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void updateSaldoPtg2(String idPedagang, int saldo, int nominal) {
        getNowTime();

        usersRef.document(current_id).collection("INV").document("SALDO")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ScanActModel model = documentSnapshot.toObject(ScanActModel.class);
                int saldoPtg = model.getSaldo();
                int plusSaldo = saldoPtg - nominal;

                usersRef.document(current_id).collection("INV").document("SALDO")
                        .update("saldo", plusSaldo,
                                "updated_at", mNowTime).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            addHistory(idPedagang, saldo, nominal, "TOP UP");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(ScanActEvent.onGetDataError, e.getMessage());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ScanActEvent.onGetDataError, e.getMessage());
            }
        });

    }

    public void addHistory(String idPedagang, int saldo, int potongan, String nama) {
        getNowTime();
        Map<String, Object> historyMap = new HashMap<>();
        historyMap.put("petugas_id", current_id);
        historyMap.put("potongan", potongan);
        historyMap.put("created_at", mNowTime);
        historyMap.put("pedagang_id", idPedagang);
        historyMap.put("jenis", nama);
        historyMap.put("sisa_saldo", saldo);

        firebaseFirestore.collection("HISTORY").document()
                .set(historyMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                postEvent(ScanActEvent.onGetDataSuccess, "Transaksi berhasil");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(ScanActEvent.onGetDataError, e.getMessage());
            }
        });
    }

    public void getNowTime() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, d MMM, yyyy");
        mNowTime = dateFormat.format(calendar.getTime());
    }

    public void postEvent(int type, String message) {
        ScanActEvent event = new ScanActEvent();

        event.setEventType(type);

        if (message != null) {
            event.setMessage(message);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }

}
