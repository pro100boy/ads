package com.loopme.ads;

import com.loopme.ads.http.request.AdvertisementCreateRequest;
import com.loopme.ads.http.request.AdvertisementUpdateRequest;
import com.loopme.ads.http.request.CampaignCreateRequest;
import com.loopme.ads.view.AdvertisementView;
import com.loopme.ads.view.CampaignView;

import java.util.Arrays;
import java.util.List;

import static com.loopme.ads.constant.Platform.*;
import static com.loopme.ads.constant.Status.*;
import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class AdsTestData {

    public static AdvertisementView adsExpectedView = AdvertisementView.builder()
            .id(3)
            .name("ads_name3")
            .status(PAUSED)
            .platforms(singletonList(WEB))
            .assetUrl("http://url3.com")
            .build();

    public static AdvertisementView adsExpectedUpdatedView = AdvertisementView.builder()
            .id(3)
            .name("new name")
            .status(FINISHED)
            .platforms(asList(IOS, ANDROID))
            .assetUrl("www.newurl.io")
            .build();

    public static AdvertisementUpdateRequest adsUpdateRequest = AdvertisementUpdateRequest.builder()
            .name("new name")
            .platforms(asList(IOS, ANDROID))
            .assetUrl("www.newurl.io")
            .status(FINISHED)
            .build();

    public static AdvertisementCreateRequest adsCreateRequest = AdvertisementCreateRequest.builder()
            .name("new ads")
            .platforms(asList(IOS, ANDROID))
            .assetUrl("http://url2new.com")
            .build();


    public static CampaignView cmpExpectedView = CampaignView.builder()
            .id(2)
            .name("COLA")
            .status(PLANNED)
            .startDate(of(2019, 02, 01, 0, 0, 0))
            .endDate(null)
            .advertisements(getAdsViews())
            .build();

    public static CampaignCreateRequest cmpCreateRequest = CampaignCreateRequest.builder()
            .name("new campaign")
            .startDate(of(2019, 02, 01, 0, 0, 0))
            .endDate(of(2019, 03, 01, 0, 0, 0))
            .advertisements(asList(1,2))
            .build();

    private static List<AdvertisementView> getAdsViews() {
        return Arrays.asList(
                AdvertisementView.builder()
                        .id(2)
                        .name("ads_name2")
                        .status(ACTIVE)
                        .platforms(singletonList(WEB))
                        .assetUrl("http://url2.com")
                        .build(),
                AdvertisementView.builder()
                        .id(3)
                        .name("ads_name3")
                        .status(PAUSED)
                        .platforms(singletonList(WEB))
                        .assetUrl("http://url3.com")
                        .build());

    }
}
