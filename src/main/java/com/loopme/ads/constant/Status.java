package com.loopme.ads.constant;

import com.loopme.ads.error.ServiceException;
import java.util.Arrays;

import static java.lang.String.format;

public enum Status {
    PLANNED(0),
    ACTIVE(1),
    PAUSED(2),
    FINISHED(3);

    private int id;

    Status(int id) {
        this.id = id;
    }

    public static Status getById(int id) {
        return Arrays.stream(Status.values())
                .filter(status -> status.id == id)
                .findFirst()
                .orElseThrow(() -> new ServiceException(format("status id %s not found", id)));
    }

    public static int getId(Status status)
    {
        return status.id;
    }
}
