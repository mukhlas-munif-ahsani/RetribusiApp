package com.munifahsan.retribusiapp.HomePedagang.pres;

import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent;
import com.munifahsan.retribusiapp.HomePedagang.repo.HomePedagangRepo;
import com.munifahsan.retribusiapp.HomePedagang.repo.HomePedagangRepoInt;
import com.munifahsan.retribusiapp.HomePedagang.view.HomePedagangViewInt;
import com.munifahsan.retribusiapp.Register.RegisterEvent;

import org.greenrobot.eventbus.Subscribe;

import static com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent.onGetDataError;
import static com.munifahsan.retribusiapp.HomePedagang.HomePedagangEvent.onGetDataSuccess;
import static com.munifahsan.retribusiapp.Register.RegisterEvent.onSignUpError;
import static com.munifahsan.retribusiapp.Register.RegisterEvent.onSignUpSuccess;

public class HomePedagangPres implements HomePedagangPresInt{
    private HomePedagangViewInt homePedagangViewInt;
    private HomePedagangRepoInt homePedagangRepoInt;
    private EventBus eventBus;

    public HomePedagangPres(HomePedagangViewInt homePedagangViewInt) {
        this.homePedagangViewInt = homePedagangViewInt;
        homePedagangRepoInt = new HomePedagangRepo();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Subscribe
    public void onEventMainThread(HomePedagangEvent event){
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
        homePedagangViewInt.loadQrCode(user_id);
        homePedagangViewInt.setSaldo(saldo);
        homePedagangViewInt.showQr();
        homePedagangViewInt.showSaldo();
        homePedagangViewInt.hideQrLoading();
        homePedagangViewInt.hideSaldoLoading();
    }


    private void onGetDataError(String error) {
        homePedagangViewInt.showMessage(error);
    }

    @Override
    public void getData() {
        homePedagangRepoInt.getData();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        homePedagangViewInt = null;
    }
}
