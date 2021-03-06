package com.loopme.ads.dao.campaign;

import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Slf4j
public class CampaignInsert extends BaseDBOperation {

    private static final String QUERY_INSERT_CAMPAIGN = "" +
            "INSERT INTO campaigns (name, status, start_date, end_date)\n" +
            "VALUES (?, ?, ?, ?)";

    private static final String QUERY_INSERT_CAMPAIGNS_ADS = "" +
            "INSERT INTO CampaignsAds(campaignID, adsID)\n" +
            "VALUES (?, ?)";


    private int insertCampaign(String name, int status, LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("insert campaign, name {}", name);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_CAMPAIGN, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, status);
            ps.setObject(3, startDate);
            ps.setObject(4, endDate);
            return ps;
        }, keyHolder);

        try {
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            log.error("An sql error occurred");
            throw new ServiceException(e);
        }
    }

    private void insertRelations(int campaignId, List<Integer> adsIds) {
        try {
            getJdbcTemplate().batchUpdate(QUERY_INSERT_CAMPAIGNS_ADS, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, campaignId);
                    ps.setInt(2, adsIds.get(i));
                }

                @Override
                public int getBatchSize() {
                    return adsIds.size();
                }
            });
        } catch (Exception e) {
            log.error("An sql error occurred");
            throw new ServiceException(e);
        }
    }

    @Transactional
    public int insert(String name, int status, LocalDateTime startDate, LocalDateTime endDate, List<Integer> adsList) {
        int id = insertCampaign(name, status, startDate, endDate);
        insertRelations(id, adsList);
        return id;
    }
}
