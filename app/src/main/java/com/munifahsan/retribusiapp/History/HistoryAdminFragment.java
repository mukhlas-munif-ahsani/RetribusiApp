package com.munifahsan.retribusiapp.History;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.munifahsan.retribusiapp.Login.view.LoginView;
import com.munifahsan.retribusiapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryAdminFragment extends Fragment {

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String mNowTime;
    private String mTanggal;
    private String mBulan;
    private String mTahun;

    @BindView(R.id.menu_item_minggu)
    FloatingActionButton minggu;
    @BindView(R.id.menu_item_bulan)
    FloatingActionButton bulan;
    @BindView(R.id.menu_item_semua)
    FloatingActionButton semua;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference historyRef = firebaseFirestore.collection("HISTORY");

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private TextView mEmptyDataTxt;
    private LinearLayoutManager mLayoutManager;
    private HistoryAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private String user_id;
    Query query;

    public HistoryAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_admin, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        toolbar = view.findViewById(R.id.Toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //toolbar.setElevation(0);

        toolbar.setTitle("Daftar Transaksi");

        ButterKnife.bind(this, view);

        mEmptyDataTxt = view.findViewById(R.id.emptyDataTxt);
        recyclerView = view.findViewById(R.id.admin_list_view);

        Log.v("user_id", user_id);

        query = historyRef.orderBy("urutan", Query.Direction.DESCENDING);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty()) {
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

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                showDialogOnLogOutBtnOnClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialogOnLogOutBtnOnClick() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set title dialog
        alertDialogBuilder.setTitle("Apakah anda yakin ingin Logout dari aplikasi ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        firebaseAuth.signOut();
                        sendToLogin();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();

    }

    private void sendToLogin() {
        Intent intent = new Intent(getActivity(), LoginView.class);
        startActivity(intent);
    }

    @OnClick(R.id.menu_item_minggu)
    public void getMingguan() {
        adapter.stopListening();

        //Toast.makeText(getActivity(),"clicked", Toast.LENGTH_LONG).show();

        query = historyRef.orderBy("urutan", Query.Direction.DESCENDING).limit(7);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty()) {
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

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.startListening();
    }

    @OnClick(R.id.menu_item_bulan)
    public void getLastBulan() {
        adapter.stopListening();

        query = historyRef.orderBy("urutan", Query.Direction.DESCENDING).limit(30);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty()) {
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

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.startListening();
    }

    @OnClick(R.id.menu_item_semua)
    public void getLastSemua() {
        adapter.stopListening();

        query = historyRef.orderBy("urutan", Query.Direction.DESCENDING);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty()) {
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

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.startListening();
    }

    @OnClick(R.id.cetak_pdf_btn)
    public void cetakPdf() {
        Toast.makeText(getActivity(), "Mohon maaf fitur cetak PDF untuk saat ini belum tersedia", Toast.LENGTH_LONG).show();
    }

    public void getTangalTime() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("d");
        mTanggal = dateFormat.format(calendar.getTime());
    }

    public void getBulanTime() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM");
        mBulan = dateFormat.format(calendar.getTime());
    }

    public void getTahunTime() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yy");
        mTahun = dateFormat.format(calendar.getTime());
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
