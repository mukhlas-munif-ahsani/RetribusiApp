package com.munifahsan.retribusiapp.MainPedagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.munifahsan.retribusiapp.History.HistoryPedagangFragment;
import com.munifahsan.retribusiapp.HomePedagang.view.HomePedagangFragment;
import com.munifahsan.retribusiapp.Profile.view.ProfileFragment;
import com.munifahsan.retribusiapp.R;

public class MainPedagang extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pedagang);

        BottomNavigationView bottomNavigationView = findViewById(R.id.pedagang_tab);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getFragmentPage(new HomePedagangFragment());
    }

    private boolean getFragmentPage(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_pedagang_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigation_home:
                fragment = new HomePedagangFragment();
                break;
//            case R.id.navigation_transfer:
//                fragment = new TopUpFragment();
//                break;
            case R.id.navigation_history:
                fragment = new HistoryPedagangFragment();
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;

        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_pedagang_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

