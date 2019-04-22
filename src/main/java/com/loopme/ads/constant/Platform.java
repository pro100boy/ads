package com.loopme.ads.constant;

import com.loopme.ads.error.ServiceException;

import java.util.Arrays;

import static java.lang.String.format;

public enum Platform {
    WEB(0),
    ANDROID(1),
    IOS(2);

    private int id;

    Platform(int id) {
        this.id = id;
    }

    public static Platform getById(int id) {
        return Arrays.stream(Platform.values())
                .filter(status -> status.id == id)
                .findFirst()
                .orElseThrow(() -> new ServiceException(format("platform id %s not found", id)));
    }

    public static int getId(Platform platform)
    {
        return platform.id;
    }
}
