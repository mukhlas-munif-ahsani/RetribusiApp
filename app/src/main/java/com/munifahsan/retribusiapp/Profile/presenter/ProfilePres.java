package com.munifahsan.retribusiapp.Profile.presenter;

import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent;
import com.munifahsan.retribusiapp.Profile.ProfileEvent;
import com.munifahsan.retribusiapp.Profile.repo.ProfileRepo;
import com.munifahsan.retribusiapp.Profile.repo.ProfileRepoInt;
import com.munifahsan.retribusiapp.Profile.view.ProfileViewInt;

import org.greenrobot.eventbus.Subscribe;

import static com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent.onGetDataError;
import static com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent.onGetDataSuccess;

public class ProfilePres implements ProfilePresInt {

    private ProfileViewInt profileViewInt;
    private ProfileRepoInt profileRepoInt;
    private EventBus eventBus;

    public ProfilePres(ProfileViewInt profileViewInt) {
        this.profileViewInt = profileViewInt;
        profileRepoInt = new ProfileRepo();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Subscribe
    public void onEventMainThread(ProfileEvent event){
        switch (event.getEventType()){
            case onGetDataSuccess:
                onGetDataSuccess(event.getNama(), event.getEmail(), event.getNohp_number(), event.getAlamat(), event.getLokasi());
                break;
            case onGetDataError:
                onGetDataError(event.getErrorMessage());
                break;
        }
    }

    private void onGetDataSuccess( String nama, String email, String nohp, String alamat, String lokasi) {
        profileViewInt.setNama(nama);
        profileViewInt.setEmail(email);
        profileViewInt.setNohp(nohp);
        profileViewInt.setAlamat(alamat);
        profileViewInt.setLokasi(lokasi);

        profileViewInt.hideShimmer();
        profileViewInt.showData();
    }

    private void onGetDataError(String error) {
        profileViewInt.showMessage(error);
    }

    @Override
    public void getData() {
        profileRepoInt.getData();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        profileViewInt = null;
    }
}
