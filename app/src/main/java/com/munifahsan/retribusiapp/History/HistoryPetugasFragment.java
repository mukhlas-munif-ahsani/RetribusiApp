package com.munifahsan.retribusiapp.History;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.munifahsan.retribusiapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryPetugasFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference historyRef = firebaseFirestore.collection("HISTORY");

    private RecyclerView recyclerView;
    private TextView mEmptyDataTxt;
    private LinearLayoutManager mLayoutManager;
    private HistoryAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private String user_id;
    Query query;

    public HistoryPetugasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_petugas, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        mEmptyDataTxt = view.findViewById(R.id.emptyDataTxt);

        Log.v("user_id", user_id);

        query = historyRef.whereEqualTo("petugas_id", user_id);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty()){
                    recyclerView.setVisibility(View.VISIBLE);
                    mEmptyDataTxt.setVisibility(View.INVISIBLE);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    mEmptyDataTxt.setVisibility(View.VISIBLE);
                }
            }
        });

        FirestoreRecyclerOptions<HistoryModel> options = new FirestoreRecyclerOptions.Builder<HistoryModel>()
                .setQuery(query, HistoryModel.class)
                .build();

        adapter = new HistoryAdapter(options);

        recyclerView = view.findViewById(R.id.history_list_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
