package com.munifahsan.retribusiapp.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.munifahsan.retribusiapp.R;
import com.munifahsan.retribusiapp.Register.pedagang.view.PedagangRegFragment;
import com.munifahsan.retribusiapp.Register.petugas.view.PetugasRegFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registerviewpagertab)
    SmartTabLayout mSmartTabLayout;
    @BindView(R.id.viewpager_register)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_register);

        ButterKnife.bind(this);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("PEDAGANG", PedagangRegFragment.class)
                .add("PETUGAS", PetugasRegFragment.class)
                .create()
        );

        mViewPager.setAdapter(adapter);
        mSmartTabLayout.setViewPager(mViewPager);
    }
}
