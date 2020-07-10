package com.munifahsan.retribusiapp.HomePetugas.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.munifahsan.retribusiapp.ScanAct.CaptureAct;
import com.munifahsan.retribusiapp.HomePetugas.pres.HomePtgPres;
import com.munifahsan.retribusiapp.HomePetugas.pres.HomePtgPresInt;
import com.munifahsan.retribusiapp.R;
import com.munifahsan.retribusiapp.ScanAct.view.ScanActActivity;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePetugasFragment extends Fragment implements HomePtgViewInt {

    private HomePtgPresInt homePtgPresInt;

    @BindView(R.id.saldo_txt)
    TextView mSaldo;
    @BindView(R.id.petugas_qr_bayar_btn)
    Button mQrButton;
    @BindView(R.id.petugas_qr_topup_btn)
    Button mQrTopupBtn;
    @BindView(R.id.saldo_shimmer)
    ShimmerFrameLayout saldoShimmer;

    public HomePetugasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_petugas, container, false);

        ButterKnife.bind(this, view);

        homePtgPresInt = new HomePtgPres(this);
        homePtgPresInt.onCreate();


        homePtgPresInt.getData();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePtgPresInt.onDestroy();
    }

    @OnClick(R.id.petugas_qr_bayar_btn)
    public void qrBayarBtnOnClick(){
        Intent intent = new Intent(getActivity().getBaseContext(), ScanActActivity.class);
        intent.putExtra("SCAN_TYPE", 1);
        getActivity().startActivity(intent);
        //scanCode();
    }

    @OnClick(R.id.petugas_qr_topup_btn)
    public void qrTopupBtnOnClick(){
        Intent intent = new Intent(getActivity().getBaseContext(), ScanActActivity.class);
        intent.putExtra("SCAN_TYPE", 2);
        getActivity().startActivity(intent);
        //scanCode();
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this.getActivity());
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Log.v("barcode result", result.getContents());
        if (result != null){
            if (result.getContents() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(result.getContents());
                builder.setTitle("Scaning result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanCode();
                    }
                }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(getActivity(), "No Result", Toast.LENGTH_LONG).show();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setSaldo(int saldo) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##,###,###");

        mSaldo.setText(decimalFormat.format(saldo));
    }

    @Override
    public void showSaldo() {
        mSaldo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSaldo() {
        mSaldo.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSaldoLoading() {
        saldoShimmer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSaldoLoading() {
        saldoShimmer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
