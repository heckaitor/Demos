package com.heckaitor.autoplay;

import com.squareup.otto.Bus;

public class BusProvider {

    private static Bus sBus;

    public static Bus getInstance() {
        if (sBus == null) {
            synchronized (BusProvider.class) {
                if (sBus == null) {
                    sBus = new Bus();
                }
            }
        }
        return sBus;
    }
}
