package com.loopme.ads.error;

import static java.lang.String.format;

public class AdvertisementNotFoundException extends RuntimeException {
    public AdvertisementNotFoundException() {
        super();
    }

    public AdvertisementNotFoundException(int id) {
        super(format("Advertisement id %s not found", id));
    }

    public AdvertisementNotFoundException(String message) {
        super(message);
    }
}
