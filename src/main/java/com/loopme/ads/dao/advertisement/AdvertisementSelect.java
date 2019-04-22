package com.loopme.ads.dao.advertisement;

import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.domain.Advertisement;
import com.loopme.ads.error.AdvertisementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

@Repository
@Slf4j
public class AdvertisementSelect extends BaseDBOperation {

    @Autowired
    private AdvertisementRowMapper rowMapper;

    private static final String QUERY_GET = "" +
            "SELECT a.id as ads_id, a.name as ads_name, a.status as ads_status, a.asset_url, a.platforms\n" +
            "FROM ads a\n" +
            "WHERE a.campaignID = ?";

    private static final String QUERY = "" +
            "SELECT a.id as ads_id, a.name as ads_name, a.status as ads_status, a.asset_url, a.platforms\n" +
            "FROM ads a\n" +
            "WHERE a.id = ?";

    public Advertisement getOne(int id) {
        try {
            return getJdbcTemplate().queryForObject(QUERY, rowMapper, id);
        } catch (EmptyResultDataAccessException e)
        {
            throw new AdvertisementNotFoundException(id);
        }
    }

    public List<Advertisement> get(List<Integer> campaignIDs) {
//        try {
//            Campaign campaign = getJdbcTemplate().queryForObject(QUERY_GET_ONE, mapperCmp, id);
//            if (nonNull(campaign)) {
//                List<Advertisement> advertisements = getJdbcTemplate().query(QUERY_GET_ADS, mapperAds, id);
//                campaign.setAdvertisements(advertisements);
//            }
//            return campaign;
//        } catch (EmptyResultDataAccessException e) {
//            log.debug("Campaign id {} not found", id);
//            return null;
//        }
        return null;
    }
}