package com.loopme.ads.dao.campaign;

import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class CampaignDelete extends BaseDBOperation {
    private static final String QUERY = "DELETE FROM campaigns WHERE id = ?";

    public int delete(int id) {
        try {
            return getJdbcTemplate().update(QUERY, id);
        } catch (Exception e) {
            log.error("An sql error occurred");
            throw new ServiceException(e);
        }
    }
}
