package com.munifahsan.retribusiapp.Profile.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munifahsan.retribusiapp.Profile.presenter.ProfilePres;
import com.munifahsan.retribusiapp.Profile.presenter.ProfilePresInt;
import com.munifahsan.retribusiapp.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileViewInt{

    private ProfilePresInt profilePresInt;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        profilePresInt = new ProfilePres(this);

        return view;
    }
}
