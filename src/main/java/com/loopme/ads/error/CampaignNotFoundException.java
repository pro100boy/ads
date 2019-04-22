package com.loopme.ads.error;

import static java.lang.String.format;

public class CampaignNotFoundException extends RuntimeException {
    public CampaignNotFoundException() {
        super();
    }

    public CampaignNotFoundException(int id) {
        super(format("Campaign id %s not found", id));
    }

    public CampaignNotFoundException(String message) {
        super(message);
    }
}
