package com.munifahsan.retribusiapp.TopUp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munifahsan.retribusiapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopUpFragment extends Fragment {

    public TopUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_up, container, false);
    }
}
