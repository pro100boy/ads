package com.loopme.ads.http.mapper;

import com.loopme.ads.domain.Campaign;
import com.loopme.ads.dto.CampaignDto;
import com.loopme.ads.http.request.CampaignCreateRequest;
import com.loopme.ads.view.CampaignListItemView;
import com.loopme.ads.view.CampaignView;

import static com.loopme.ads.constant.Status.*;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class CampaignMapper {
    public static CampaignView toView(Campaign campaign) {
        if (isNull(campaign)) {
            return null;
        }

        CampaignView view = new CampaignView();

        view.setId(campaign.getId());
        view.setName(campaign.getName());
        view.setStatus(getById(campaign.getStatus()));
        view.setStartDate(campaign.getStartDate());
        view.setEndDate(campaign.getEndDate());

        view.setAdvertisements(
                campaign.getAdvertisements().stream().map(AdvertisementMapper::toView).collect(toList()));

        return view;
    }

    public static CampaignDto toDto(CampaignCreateRequest request) {
        CampaignDto dto = new CampaignDto();

        dto.setName(request.getName());
        dto.setStatus(getId(PLANNED));
        dto.setStartDate(request.getStartDate());
        dto.setEndDate(request.getEndDate());
        dto.setAdvertisements(request.getAdvertisements());

        return dto;
    }

    public static CampaignListItemView toListItemView(CampaignDto dto) {
        CampaignListItemView view = new CampaignListItemView();

        view.setName(dto.getName());
        view.setStatus(getById(dto.getStatus()));
        view.setStartDate(dto.getStartDate());
        view.setEndDate(dto.getEndDate());
        view.setAdvertisements(dto.getAdvertisements());

        return view;
    }
}
