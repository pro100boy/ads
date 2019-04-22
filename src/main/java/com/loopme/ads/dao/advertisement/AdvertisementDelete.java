package com.loopme.ads.dao.advertisement;

import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Repository
@Slf4j
public class AdvertisementDelete extends BaseDBOperation {

    private static final String QUERY = "DELETE FROM ads WHERE id = ?";

    @Transactional
    public int delete(int id) {
        try {
            return getJdbcTemplate().update(QUERY, id);
        } catch (Exception e) {
            log.error("An sql error occurred");
            throw new ServiceException(e);
        }
    }
}