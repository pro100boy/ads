package com.loopme.ads.service;

import com.loopme.ads.dao.advertisement.AdvertisementDelete;
import com.loopme.ads.dao.advertisement.AdvertisementInsert;
import com.loopme.ads.dao.advertisement.AdvertisementSelect;
import com.loopme.ads.dao.advertisement.AdvertisementUpdate;
import com.loopme.ads.domain.Advertisement;
import com.loopme.ads.error.AdvertisementNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdvertisementService {
    private final AdvertisementSelect advertisementSelect;
    private final AdvertisementDelete advertisementDelete;
    private final AdvertisementInsert advertisementInsert;
    private final AdvertisementUpdate advertisementUpdate;

    public Advertisement get(int id) {
        return advertisementSelect.getOne(id);
    }

    public void delete(int id) {
        if (advertisementDelete.delete(id) == 0) {
            throw new AdvertisementNotFoundException(id);
        }
    }

    public int create(Advertisement advertisement) {
        return advertisementInsert.insert(advertisement);
    }

    public void update(Advertisement advertisement) {
        if (advertisementUpdate.update(
                advertisement.getId(),
                advertisement.getName(),
                advertisement.getStatus(),
                advertisement.getPlatforms(),
                advertisement.getAssetUrl()) == 0) {
            throw new AdvertisementNotFoundException(advertisement.getId());
        }
    }
}
