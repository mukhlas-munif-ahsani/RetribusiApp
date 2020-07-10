package com.munifahsan.retribusiapp.ScanAct.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.munifahsan.retribusiapp.MainPetugas.MainPetugas;
import com.munifahsan.retribusiapp.R;
import com.munifahsan.retribusiapp.ScanAct.CaptureAct;
import com.munifahsan.retribusiapp.ScanAct.pres.ScanActPres;
import com.munifahsan.retribusiapp.ScanAct.pres.ScanActPresInt;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanActActivity extends AppCompatActivity implements ScanActViewInt {

    private ScanActPresInt scanActPresInt;

    @BindView(R.id.bayar)
    LinearLayout mBayar;
    @BindView(R.id.selesai)
    LinearLayout mSelesai;
    private int scanType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_act);

        ButterKnife.bind(this);

        scanActPresInt = new ScanActPres(this);
        scanActPresInt.onCreate();

        Intent intent = getIntent();
        scanType = intent.getIntExtra("SCAN_TYPE", 0);

        scanCode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanActPresInt.onDestroy();
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                if (scanType == 1) {
//                    Toast.makeText(this, "Tipe Scan 1 : " + result.getContents(), Toast.LENGTH_LONG).show();
                    bayarPajak(result.getContents());
                }

                if (scanType == 2) {
                    Toast.makeText(this, "Tipe Scan 2 : " + result.getContents(), Toast.LENGTH_LONG).show();
                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage(result.getContents());
//                builder.setTitle("Scaning result");
//                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        scanCode();
//                    }
//                }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
            } else {
                Toast.makeText(this, "No Result", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void bayarPajak(String qrId) {
        scanActPresInt.getDataBayar(qrId);
    }

    public void topUp() {

    }

    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showBayar() {
        mBayar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBayar() {
        mBayar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSelesai() {
        mSelesai.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSelesai() {
        mSelesai.setVisibility(View.INVISIBLE);
    }

    public void navigatePtgDelay(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToHomePtg();
            }
        }, 3000);
    }

    public void navigateToHomePtg() {

        Intent intent = new Intent(this, MainPetugas.class);
        startActivity(intent);
        finish();

    }
}
