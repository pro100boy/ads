package com.loopme.ads.dao.advertisement;

import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Slf4j
public class AdvertisementUpdate extends BaseDBOperation {

    private static final String QUERY = "" +
            "UPDATE ads SET name = ?, status = ?, platforms = ?, asset_url = ?\n" +
            "WHERE id = ?";

    @Transactional
    public int update(int id, String name, int status, List<Integer> platformIds, String url) {
        try {
            return getJdbcTemplate().update(QUERY, name, status, platformIds.toArray(), url, id);
        } catch (Exception e) {
            log.error("An sql error occurred");
            throw new ServiceException(e);
        }
    }
}