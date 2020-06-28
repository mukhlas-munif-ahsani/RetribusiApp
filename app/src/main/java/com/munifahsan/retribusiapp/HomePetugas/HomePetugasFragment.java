package com.munifahsan.retribusiapp.HomePetugas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munifahsan.retribusiapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePetugasFragment extends Fragment {

    public HomePetugasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_petugas, container, false);
    }
}
