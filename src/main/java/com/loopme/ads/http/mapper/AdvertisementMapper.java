package com.loopme.ads.http.mapper;

import com.loopme.ads.constant.Platform;
import com.loopme.ads.domain.Advertisement;
import com.loopme.ads.http.request.AdvertisementCreateRequest;
import com.loopme.ads.http.request.AdvertisementUpdateRequest;
import com.loopme.ads.view.AdvertisementView;

import static com.loopme.ads.constant.Status.*;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class AdvertisementMapper {

    public static AdvertisementView toView(Advertisement ads) {
        if (isNull(ads)) {
            return null;
        }

        AdvertisementView view = new AdvertisementView();

        view.setId(ads.getId());
        view.setName(ads.getName());
        view.setStatus(getById(ads.getStatus()));
        view.setAssetUrl(ads.getAssetUrl());

        view.setPlatforms(
                ads.getPlatforms().stream().map(Platform::getById).collect(toList()));

        return view;
    }

    public static Advertisement toEntity(AdvertisementCreateRequest request) {
        return Advertisement.builder()
                .name(request.getName())
                .assetUrl(request.getAssetUrl())
                .status(getId(PLANNED))
                .platforms(request.getPlatforms().stream().map(Platform::getId).collect(toList()))
                .build();
    }

    public static Advertisement toEntity(AdvertisementUpdateRequest request) {
        return Advertisement.builder()
                .name(request.getName())
                .assetUrl(request.getAssetUrl())
                .status(getId(request.getStatus()))
                .platforms(request.getPlatforms().stream().map(Platform::getId).collect(toList()))
                .build();
    }
}
