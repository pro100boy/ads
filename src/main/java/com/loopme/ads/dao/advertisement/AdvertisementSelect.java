package com.loopme.ads.dao.advertisement;

import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.domain.Advertisement;
import com.loopme.ads.error.AdvertisementNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdvertisementSelect extends BaseDBOperation {

    private final AdvertisementRowMapper rowMapper;

    private static final String QUERY = "" +
            "SELECT a.id as ads_id, a.name as ads_name, a.status as ads_status, a.asset_url, a.platforms\n" +
            "FROM ads a\n" +
            "WHERE a.id = ?";

    public Advertisement get(int id) {
        try {
            return getJdbcTemplate().queryForObject(QUERY, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new AdvertisementNotFoundException(id);
        }
    }
}