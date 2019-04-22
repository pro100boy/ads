package com.loopme.ads.dao.campaign;

import com.loopme.ads.constant.CampaignSort;
import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.dao.advertisement.AdvertisementRowMapper;
import com.loopme.ads.domain.Advertisement;
import com.loopme.ads.domain.Campaign;
import com.loopme.ads.dto.CampaignDto;
import com.loopme.ads.error.CampaignNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.isEmpty;

@Repository
@Slf4j
public class CampaignSelect extends BaseDBOperation {

    @Autowired
    private AdvertisementRowMapper advertisementMapper;

    private static final String QUERY_GET_ONE = "" +
            "SELECT c.id     as cmp_id,\n" +
            "       c.name   as cmp_name,\n" +
            "       c.status as cmp_status,\n" +
            "       c.start_date,\n" +
            "       c.end_date,\n" +
            "       a.id     as ads_id,\n" +
            "       a.name   as ads_name,\n" +
            "       a.status as ads_status,\n" +
            "       a.asset_url,\n" +
            "       a.platforms\n" +
            "FROM campaigns c\n" +
            "         INNER JOIN CampaignsAds ca on c.id = ca.campaignID\n" +
            "         INNER JOIN ads a on ca.adsID = a.id\n" +
            "WHERE c.id = ?";

//    private static final String QUERY_GET = "" +
//            "SELECT c.id, c.name, c.status, count(a.id) as cnt\n" +
//            "FROM campaigns c\n" +
//            "INNER JOIN ads a on c.id = a.campaignID\n" +
//            "GROUP BY c.id, c.name, c.status\n" +
//            //"HAVING (@status IS NULL OR c.status=@status) AND (@searchStr IS NULL OR LOWER(c.name) LIKE @searchStr)\n" +
//            "%s\n" +
//            "ORDER BY %s\n" +
//            "OFFSET ? LIMIT ?";
//
//    private final RowMapper<Campaign> mapperCmp = (rs, i) -> Campaign.builder()
//            .id(rs.getInt("id"))
//            .name(rs.getNString("name"))
//            .status(rs.getInt("status"))
//            .advertisements(rs.getInt("cnt"))
//            .build();

    private class CampaignExtractor implements ResultSetExtractor<Campaign> {
        @Override
        public Campaign extractData(ResultSet rs) throws SQLException {
            Campaign c = null;

            while (rs.next()) {
                if (isNull(c)) {
                    c = new Campaign();
                    c.setId(rs.getInt("cmp_id"));
                    c.setName(rs.getNString("cmp_name"));
                    c.setStatus(rs.getInt("cmp_status"));
                    c.setStartDate(rs.getObject("start_date", LocalDateTime.class));
                    c.setEndDate(rs.getObject("end_date", LocalDateTime.class));
                    c.setAdvertisements(new ArrayList<>());
                }

                Advertisement a = advertisementMapper.mapRow(rs, rs.getRow());
                c.getAdvertisements().add(a);
            }

            return c;
        }
    }

    public Campaign getOne(int id) {

        Campaign campaign = getJdbcTemplate().query(QUERY_GET_ONE, new CampaignExtractor(), id);
        if (isNull(campaign)) {
            throw new CampaignNotFoundException(id);
        }
        return campaign;
    }

//    public List<CampaignDto> get(Integer status, String search, String order, CampaignSort sort, int offset, int limit) {
//        String searchStr = isEmpty(search) ? null : "'%" + search + "%'";
//
//        try {
//            return getJdbcTemplate().query(getQuery(sort, order, searchStr, status), mapperCmp, offset, limit);
//        } catch (EmptyResultDataAccessException e) {
//            log.debug("Campaigns weren't find, offset {} limit {}", offset, limit);
//            return emptyList();
//        } catch (UncategorizedSQLException ex) {
//            log.error(ex.getMessage());
//            throw new RuntimeException(ex);
//        }
//    }
//
//    private String getQuery(CampaignSort sort, String order, String searchStr, Integer status) {
//        if (sort == null) {
//            sort = CampaignSort.NAME;
//        }
//
//        order = (isEmpty(order) || order.equalsIgnoreCase("ASC")) ? "ASC" : "DESC";
//        String orderParam = null;
//
//        switch (sort) {
//            case NAME:
//                orderParam = "c.name " + order;
//                break;
//            case STATUS:
//                orderParam = "c.status " + order;
//                break;
//            case ADS:
//                orderParam = "count(a.id) " + order;
//                break;
//        }
//
//        String having = createHaving(searchStr, status);
//
//        return format(QUERY_GET, having, orderParam);
//    }
//
//    private String createHaving(String searchStr, Integer status) {
//        if (isEmpty(searchStr) && isNull(status)) return "";
//
//        if (!isEmpty(searchStr) && isNull(status)) return format("HAVING LOWER(c.name) LIKE %s", searchStr);
//        else if (isEmpty(searchStr) && nonNull(status)) return format("HAVING c.status=%s", status);
//        else return format("HAVING LOWER(c.name) LIKE %s AND c.status=%s", searchStr, status);
//
//    }
}