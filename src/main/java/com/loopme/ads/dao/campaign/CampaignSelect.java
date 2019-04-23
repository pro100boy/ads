package com.loopme.ads.dao.campaign;

import com.loopme.ads.constant.CampaignSort;
import com.loopme.ads.constant.SearchOrder;
import com.loopme.ads.constant.Status;
import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.dao.advertisement.AdvertisementRowMapper;
import com.loopme.ads.domain.Advertisement;
import com.loopme.ads.domain.Campaign;
import com.loopme.ads.dto.CampaignListItemDto;
import com.loopme.ads.error.CampaignNotFoundException;
import com.loopme.ads.http.request.ListQuery;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CampaignSelect extends BaseDBOperation {

    private final AdvertisementRowMapper advertisementMapper;

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

    private static final String QUERY_SEARCH = "" +
            "SELECT c.id, c.name, c.status, count(a.id) as cnt\n" +
            "FROM campaigns c\n" +
            "       INNER JOIN CampaignsAds ca ON c.id = ca.campaignID\n" +
            "       INNER JOIN ads a ON ca.campaignID = a.id\n" +
            "GROUP BY c.id, c.name, c.status\n" +
            "%s\n" + //"HAVING c.status=@status AND LOWER(c.name) LIKE @searchStr" +
            "ORDER BY %s\n" +
            "OFFSET ? LIMIT ?";

    private static final String HAVING = "HAVING ";
    private static final String HAVING_NAME_CLAUSE = "LOWER(c.name) LIKE %s ";
    private static final String HAVING_STATUS_CLAUSE = "c.status=%s ";
    private static final String HAVING_CLAUSE = HAVING + HAVING_NAME_CLAUSE + " AND " + HAVING_STATUS_CLAUSE;

    private final RowMapper<CampaignListItemDto> mapperCmp = (rs, i) -> CampaignListItemDto.builder()
            .id(rs.getInt("id"))
            .name(rs.getNString("name"))
            .status(rs.getInt("status"))
            .adsCnt(rs.getInt("cnt"))
            .build();

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

    public List<CampaignListItemDto> search(ListQuery params) {
        String searchStr = isEmpty(params.getSearch()) ? null : "N'%" + params.getSearch() + "%'";

        try {
            String query = getQuery(params, searchStr);
            return getJdbcTemplate().query(query, mapperCmp, params.getOffset(), params.getLimit());
        } catch (EmptyResultDataAccessException e) {
            log.debug("Campaigns weren't find, offset {} limit {}", params.getOffset(), params.getLimit());
            return emptyList();
        } catch (UncategorizedSQLException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private String getQuery(ListQuery params, String searchStr) {
        String orderClause = null;
        String order = params.getOrder().name();

        switch (params.getSort()) {
            case NAME:
                orderClause = "c.name " + order;
                break;
            case STATUS:
                orderClause = "c.status " + order;
                break;
            case ADS:
                orderClause = "count(a.id) " + order;
                break;
        }

        String having = createHaving(searchStr, params.getStatus());

        return format(QUERY_SEARCH, having, orderClause);
    }

    private String createHaving(String searchStr, Status status) {
        if (isEmpty(searchStr) && isNull(status)) return "";

        if (!isEmpty(searchStr) && isNull(status)) return format(HAVING + HAVING_NAME_CLAUSE, searchStr);
        else if (isEmpty(searchStr) && nonNull(status)) return format(HAVING + HAVING_STATUS_CLAUSE, Status.getId(status));
        else return format(HAVING_CLAUSE, searchStr, status);
    }
}