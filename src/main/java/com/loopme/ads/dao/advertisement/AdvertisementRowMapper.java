package com.loopme.ads.dao.advertisement;

import com.loopme.ads.domain.Advertisement;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

@Component
public class AdvertisementRowMapper implements RowMapper<Advertisement> {

    @Override
    public Advertisement mapRow(ResultSet rs, int i) throws SQLException {
        Advertisement a = new Advertisement();
        a.setId(rs.getInt("ads_id"));
        a.setName(rs.getNString("ads_name"));
        a.setStatus(rs.getInt("ads_status"));
        a.setAssetUrl(rs.getString("asset_url"));
        Object[] array = (Object[]) rs.getArray("platforms").getArray();
        a.setPlatforms(Arrays.stream(array).map(item -> parseInt(item.toString())).collect(toList()));
        return a;
    }
}
