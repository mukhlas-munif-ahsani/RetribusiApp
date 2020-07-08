package com.munifahsan.retribusiapp.HomePedagang.view;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.munifahsan.retribusiapp.HomePedagang.pres.HomePedagangPres;
import com.munifahsan.retribusiapp.HomePedagang.pres.HomePedagangPresInt;
import com.munifahsan.retribusiapp.R;

import java.text.DecimalFormat;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePedagangFragment extends Fragment implements HomePedagangViewInt{

    private HomePedagangPresInt homePedagangPresInt;

    @BindView(R.id.saldo_txt)
    TextView mSaldo;
    @BindView(R.id.pedagang_qr_image)
    ImageView mQrImage;
    @BindView(R.id.saldo_shimmer)
    ShimmerFrameLayout saldoShimmer;
    @BindView(R.id.qr_shimmer)
    ShimmerFrameLayout qrShimmer;


    public HomePedagangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home_pedagang, container, false);
        View view = inflater.inflate(R.layout.fragment_home_pedagang, container, false);

        ButterKnife.bind(this, view);

        homePedagangPresInt = new HomePedagangPres(this);
        homePedagangPresInt.onCreate();
        String data = "4v9CiiV0zeTrE2kVAFurWiO6u302";

//        loadQrCode(data);

        homePedagangPresInt.getData();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePedagangPresInt.onDestroy();
    }

    public void loadQrCode(String data){
        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 700);
        qrgEncoder.setColorBlack(getActivity().getResources().getColor(R.color.black));
        qrgEncoder.setColorWhite(getActivity().getResources().getColor(R.color.grey_200));
        try {
            Bitmap bitmap = qrgEncoder.getBitmap();
            mQrImage.setImageBitmap(bitmap);
        } catch (Exception e){
            Log.v("QR Eror", e.toString());
        }
    }

    @Override
    public void setSaldo(int saldo) {
//        Locale localeID = new Locale("in", "ID");
//        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        DecimalFormat decimalFormat = new DecimalFormat("#,##,###,###");
       // String prezzo = decimalFormat.format(saldo);

        mSaldo.setText(decimalFormat.format(saldo));
    }

    @Override
    public void showQr() {
        mQrImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideQr() {
        mQrImage.setVisibility(View.INVISIBLE);
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
    public void showQrLoading() {
        qrShimmer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideQrLoading() {
        qrShimmer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
