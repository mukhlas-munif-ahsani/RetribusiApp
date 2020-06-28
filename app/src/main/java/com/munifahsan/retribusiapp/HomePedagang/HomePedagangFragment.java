package com.munifahsan.retribusiapp.HomePedagang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munifahsan.retribusiapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePedagangFragment extends Fragment {

    public HomePedagangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
