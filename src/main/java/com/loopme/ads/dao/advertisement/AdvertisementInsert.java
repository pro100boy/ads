package com.loopme.ads.dao.advertisement;

import com.loopme.ads.dao.BaseDBOperation;
import com.loopme.ads.domain.Advertisement;
import com.loopme.ads.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
@Slf4j
public class AdvertisementInsert extends BaseDBOperation {

    private static final String QUERY = "" +
            "INSERT INTO ads (name, asset_url, status, platforms)\n" +
            "VALUES (?, ?, ?, ?)";

    @Transactional
    public int insert(Advertisement advertisement) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, advertisement.getName());
            ps.setString(2, advertisement.getAssetUrl());
            ps.setInt(3, advertisement.getStatus());
            ps.setObject(4, advertisement.getPlatforms().toArray());
            return ps;
        }, keyHolder);

        try {
            return keyHolder.getKey().intValue();
        } catch (NullPointerException e) {
            log.error("An sql error occurred");
            throw new ServiceException(e);
        }
    }
}
