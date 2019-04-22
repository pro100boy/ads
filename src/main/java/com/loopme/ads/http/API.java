package com.loopme.ads.http;

public interface API {
    String ROOT = "/api";
    String V1 = "/v1";
    String ROOT_PATH_V1 = ROOT + V1;
    String SPECIFIC = "/{id}";

    interface Advertisement{
        String PATH = "/ad";
        String CONCRETE = PATH + SPECIFIC;
    }

    interface Campaign{
        String PATH = "/campaign";
        String CONCRETE = PATH + SPECIFIC;
        String LIST = "/summaries";
    }
}
