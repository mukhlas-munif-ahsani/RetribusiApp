package com.munifahsan.retribusiapp.ScanAct.repo;

public interface ScanActRepoInt {

    void proceedTopup(String idPedagang, int nominal);
    void proceedPajak(String qrId);
}
