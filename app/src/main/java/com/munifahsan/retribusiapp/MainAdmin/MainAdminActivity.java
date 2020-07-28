package com.munifahsan.retribusiapp.MainAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.munifahsan.retribusiapp.History.HistoryAdminFragment;
import com.munifahsan.retribusiapp.R;

public class MainAdminActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        getFragmentPage(new HistoryAdminFragment());
    }

    private boolean getFragmentPage(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_admin_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
