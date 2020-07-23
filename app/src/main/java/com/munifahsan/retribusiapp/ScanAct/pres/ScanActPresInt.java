package com.munifahsan.retribusiapp.ScanAct.pres;

import com.munifahsan.retribusiapp.BasePressInt;

public interface ScanActPresInt extends BasePressInt {
    void proceedTopup(String idPedagang, int nominal);
    void proceedPajak(String qrId);
}
