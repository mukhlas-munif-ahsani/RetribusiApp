package com.munifahsan.retribusiapp.ScanAct.pres;

import com.munifahsan.retribusiapp.EventBuss.EventBus;
import com.munifahsan.retribusiapp.EventBuss.GreenRobotEventBus;
import com.munifahsan.retribusiapp.ScanAct.ScanActEvent;
import com.munifahsan.retribusiapp.ScanAct.repo.ScanActRepo;
import com.munifahsan.retribusiapp.ScanAct.repo.ScanActRepoInt;
import com.munifahsan.retribusiapp.ScanAct.view.ScanActViewInt;

import org.greenrobot.eventbus.Subscribe;

import static com.munifahsan.retribusiapp.ScanAct.ScanActEvent.onGetDataError;
import static com.munifahsan.retribusiapp.ScanAct.ScanActEvent.onGetDataSuccess;


public class ScanActPres implements ScanActPresInt{
    private ScanActViewInt scanActViewInt;
    private ScanActRepoInt scanActRepoInt;
    private EventBus eventBus;

    public ScanActPres(ScanActViewInt scanActViewInt) {
        this.scanActViewInt = scanActViewInt;
        scanActRepoInt = new ScanActRepo();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        scanActViewInt = null;
        eventBus.unregister(this);
    }

    @Subscribe
    public void onEventMainThread(ScanActEvent event){
        switch (event.getEventType()){
            case onGetDataSuccess:
                onGetDataSuccess(event.getMessage());
                break;
            case onGetDataError:
                onGetDataError(event.getMessage());
                break;
        }
    }

    private void onGetDataSuccess(String message) {
        scanActViewInt.showSelesai();
        scanActViewInt.hideBayar();
        scanActViewInt.navigatePtgDelay();
    }

    private void onGetDataError(String message) {
        scanActViewInt.showMessage(message);
        scanActViewInt.navigateToHomePtg();
    }

    @Override
    public void proceedTopup(String idPedagang, int nominal) {
        scanActViewInt.hideSelesai();
        scanActViewInt.hideTopUpLay();
        scanActViewInt.showBayar();
        scanActRepoInt.proceedTopup(idPedagang, nominal);
    }

    public void proceedPajak(String idPedagang){
        scanActViewInt.hideSelesai();
        scanActViewInt.showBayar();
        scanActRepoInt.proceedPajak(idPedagang);
    }
}
