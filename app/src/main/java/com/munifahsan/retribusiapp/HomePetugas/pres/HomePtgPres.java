package com.munifahsan.retribusiapp.HomePetugas.pres;

import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent;
import com.munifahsan.retribusiapp.HomePedagang.repo.HomePedagangRepoInt;
import com.munifahsan.retribusiapp.HomePetugas.HomePtgEvent;
import com.munifahsan.retribusiapp.HomePetugas.repo.HomePtgRepo;
import com.munifahsan.retribusiapp.HomePetugas.repo.HomePtgRepoInt;
import com.munifahsan.retribusiapp.HomePetugas.view.HomePtgViewInt;

import org.greenrobot.eventbus.Subscribe;

import static com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent.onGetDataError;
import static com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent.onGetDataSuccess;

public class HomePtgPres implements HomePtgPresInt{

    private HomePtgViewInt homePtgViewInt;
    private HomePtgRepoInt homePtgRepoInt;
    private EventBus eventBus;

    public HomePtgPres(HomePtgViewInt homePtgViewInt) {
        this.homePtgViewInt = homePtgViewInt;
        homePtgRepoInt = new HomePtgRepo();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        homePtgViewInt = null;
        eventBus.unregister(this);
    }

    @Subscribe
    public void onEventMainThread(HomePtgEvent event){
        switch (event.getEventType()){
            case onGetDataSuccess:
                onGetDataSuccess(event.getUser_id(), event.getSaldo());
                break;
            case onGetDataError:
                onGetDataError(event.getErrorMessage());
                break;
        }
    }

    private void onGetDataSuccess(String user_id, int saldo){
        homePtgViewInt.setSaldo(saldo);
        homePtgViewInt.showSaldo();
        homePtgViewInt.hideSaldoLoading();
    }


    private void onGetDataError(String error) {
        homePtgViewInt.showMessage(error);
    }

    @Override
    public void getData() {
        homePtgRepoInt.getData();
    }
}
