package com.munifahsan.retribusiapp.History;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.munifahsan.retribusiapp.R;

import java.text.DecimalFormat;

public class HistoryAdminAdapter extends FirestoreRecyclerAdapter<HistoryModel, HistoryAdminAdapter.Holder> {

    public HistoryAdminAdapter(@NonNull FirestoreRecyclerOptions<HistoryModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HistoryAdminAdapter.Holder holder, int position, @NonNull HistoryModel model) {
        holder.mJenis.setText(model.getJenis());
        holder.mTime.setText(model.getCreated_at());
        holder.mNamaPedagang.setText(model.getNama_pedagang());
        //holder.mMoney.setText(String.valueOf(model.getPotongan()));

        if (model.getJenis().equals("PAJAK")) {
            holder.mPlus.setVisibility(View.INVISIBLE);
            holder.mMin.setVisibility(View.VISIBLE);
            holder.mImage.setImageResource(R.drawable.pajak_icon);
        }

        if (model.getJenis().equals("TOP UP")) {
            holder.mPlus.setVisibility(View.VISIBLE);
            holder.mMin.setVisibility(View.INVISIBLE);
            holder.mImage.setImageResource(R.drawable.top_up_ic);
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,##,###,###");
        holder.mMoney.setText(decimalFormat.format(model.getPotongan()));

    }

    @NonNull
    @Override
    public HistoryAdminAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_admin, parent, false);
        return new Holder(view);
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mJenis;
        private TextView mTime;
        private TextView mMoney;
        private TextView mNamaPedagang;
        private TextView mRp;
        private TextView mMin;
        private TextView mPlus;
        private ImageView mImage;

        public Holder(@NonNull View itemView) {
            super(itemView);

            mJenis = itemView.findViewById(R.id.jenisTxt);
            mTime = itemView.findViewById(R.id.timeTxt);
            mMoney = itemView.findViewById(R.id.moneyTxt);
            mNamaPedagang = itemView.findViewById(R.id.nama_pedagang);
            mRp = itemView.findViewById(R.id.rpTxt);
            mMin = itemView.findViewById(R.id.minTxt);
            mPlus = itemView.findViewById(R.id.plusTxt);
            mImage = itemView.findViewById(R.id.image);
        }
    }
}
