package com.loopme.ads.dao.campaign;

import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Slf4j
public class CampaignUpdate extends BaseDBOperation {

    private static final String QUERY_UPDATE_CAMPAIGN = "" +
            "UPDATE campaigns SET name = ?, status = ?, start_date = ?, end_date = ?\n" +
            "WHERE id = ?";

    private static final String QUERY_DELETE_CAMPAIGNS_ADS = "" +
            "DELETE FROM CampaignsAds WHERE campaignID = ?";

    private static final String QUERY_INSERT_CAMPAIGNS_ADS = "" +
            "INSERT INTO CampaignsAds(campaignID, adsID)\n" +
            "VALUES (?, ?)";

    @Transactional
    public int update(int id, String name, Integer status, LocalDateTime startDate, LocalDateTime endDate, List<Integer> adsIds) {
        try {
            if (getJdbcTemplate().update(QUERY_UPDATE_CAMPAIGN, name, status, startDate, endDate, id) == 1) {
                deleteRelations(id);
                insertRelations(id, adsIds);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            log.error("An sql error occurred");
            throw new ServiceException(e);
        }
    }

    private void deleteRelations(int campaignId) {
        try {
            getJdbcTemplate().update(QUERY_DELETE_CAMPAIGNS_ADS, campaignId);
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
}
