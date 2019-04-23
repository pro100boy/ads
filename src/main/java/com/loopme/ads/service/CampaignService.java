package com.loopme.ads.service;

import com.loopme.ads.dao.campaign.CampaignDelete;
import com.loopme.ads.dao.campaign.CampaignInsert;
import com.loopme.ads.dao.campaign.CampaignSelect;
import com.loopme.ads.dao.campaign.CampaignUpdate;
import com.loopme.ads.domain.Campaign;
import com.loopme.ads.dto.CampaignDto;
import com.loopme.ads.dto.CampaignListItemDto;
import com.loopme.ads.error.CampaignNotFoundException;
import com.loopme.ads.http.request.ListQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CampaignService {

    private final CampaignSelect campaignSelect;
    private final CampaignDelete campaignDelete;
    private final CampaignInsert campaignInsert;
    private final CampaignUpdate campaignUpdate;

    public Campaign get(int id) {
        return campaignSelect.getOne(id);
    }

    public void delete(int id) {
        if (campaignDelete.delete(id) == 0) {
            throw new CampaignNotFoundException(id);
        }
    }

    public int create(CampaignDto campaign) {
        return campaignInsert.insert(
                campaign.getName(),
                campaign.getStatus(),
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getAdvertisements()
        );
    }

    public List<CampaignListItemDto> search(ListQuery queryParams) {
        return campaignSelect.search(queryParams);
    }

    public void update(int id, CampaignDto campaign) {
        if (campaignUpdate.update(
                id,
                campaign.getName(),
                campaign.getStatus(),
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getAdvertisements()) == 0) {
            throw new CampaignNotFoundException(id);
        }
    }
}
