package com.loopme.ads;

import com.loopme.ads.dao.advertisement.AdvertisementSelect;
import com.loopme.ads.domain.Advertisement;
import com.loopme.ads.error.AdvertisementNotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

public class AdvertisementServiceTest extends TestConfig{
    @Autowired
    private AdvertisementSelect select;

    @Test
    public void should_get_by_id() {
        Advertisement expected = Advertisement.builder()
                .id(3)
                .name("ads_name3")
                .status(2)
                .platforms(singletonList(0))
                .assetUrl("http://url3.com")
                .build();

        Advertisement actual = select.getOne(3);

        assertThat(actual, is(samePropertyValuesAs(expected)));
    }

    @Test(expected = AdvertisementNotFoundException.class)
    public void should_get_by_id1() {
        select.getOne(30);
    }
}
