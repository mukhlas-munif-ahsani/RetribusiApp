package com.munifahsan.retribusiapp.Profile.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.munifahsan.retribusiapp.Login.view.LoginView;
import com.munifahsan.retribusiapp.Profile.presenter.ProfilePres;
import com.munifahsan.retribusiapp.Profile.presenter.ProfilePresInt;
import com.munifahsan.retribusiapp.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileViewInt{

    private ProfilePresInt profilePresInt;
    private FirebaseAuth mAuth;

    @BindView(R.id.profile_shimmer)
    ShimmerFrameLayout mProfileShimmer;
    @BindView(R.id.profile_data)
    RelativeLayout mProfileData;
    @BindView(R.id.profile_name)
    TextView mNama;
    @BindView(R.id.profile_email)
    TextView mEmail;
    @BindView(R.id.profile_nohp)
    TextView mNohp;
    @BindView(R.id.profile_alamat)
    TextView mAlamat;
    @BindView(R.id.profile_lokasi)
    TextView mLokasi;

    @BindView(R.id.profile_logout)
    Button mProfileLogout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        mAuth = FirebaseAuth.getInstance();

        profilePresInt = new ProfilePres(this);
        profilePresInt.onCreate();

        profilePresInt.getData();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profilePresInt.onDestroy();
    }

    @Override
    public void showMessage(String txt) {
        Toast.makeText(getActivity(), txt, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSimmer() {
        mProfileShimmer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShimmer() {
        mProfileShimmer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showData() {
        mProfileData.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideData() {
        mProfileData.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setNama(String nama) {
        mNama.setText(nama);
    }

    @Override
    public void setEmail(String email) {
        mEmail.setText(email);
    }

    @Override
    public void setNohp(String nohp) {
        mNohp.setText(nohp);
    }

    @Override
    public void setAlamat(String alamat) {
        mAlamat.setText(alamat);
    }

    @Override
    public void setLokasi(String lokasi) {
        mLokasi.setText(lokasi);
    }

    @OnClick(R.id.profile_logout)
    public void logoutOnClick(){
        showDialogOnLogOutBtnOnClick();
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
                        mAuth.signOut();
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
        getActivity().finish();
    }
}
