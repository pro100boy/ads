package com.loopme.ads.controller;

import com.loopme.ads.http.request.CampaignCreateRequest;
import com.loopme.ads.view.CampaignListItemView;
import com.loopme.ads.view.CampaignView;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.loopme.ads.AdsTestData.cmpCreateRequest;
import static com.loopme.ads.AdsTestData.cmpExpectedView;
import static com.loopme.ads.http.API.Campaign.CONCRETE;
import static com.loopme.ads.http.API.Campaign.PATH;
import static com.loopme.ads.http.API.ROOT_PATH_V1;
import static com.loopme.ads.http.mapper.CampaignMapper.toDto;
import static com.loopme.ads.http.mapper.CampaignMapper.toListItemView;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CampaignControllerTest extends AbstractControllerTest {
    @Test
    public void should_get() throws Exception {
        CampaignView expected = cmpExpectedView;

        MvcResult mvcResult = mockMvc.perform(get(ROOT_PATH_V1 + CONCRETE, 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();

        CampaignView actual = getGson().fromJson(mvcResult.getResponse().getContentAsString(), CampaignView.class);
        assertThat(actual, is(samePropertyValuesAs(expected)));
    }

    @Test
    public void should_delete_by_id() throws Exception {
        mockMvc.perform(delete(ROOT_PATH_V1 + CONCRETE, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(get(ROOT_PATH_V1 + CONCRETE, 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_create() throws Exception {
        CampaignCreateRequest request = cmpCreateRequest;

        MvcResult mvcResult = mockMvc.perform(
                post(ROOT_PATH_V1 + PATH)
                        .contentType(APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();

        CampaignListItemView actual = getGson().fromJson(
                mvcResult.getResponse().getContentAsString(), CampaignListItemView.class);

        assertTrue(actual.getId()>0);

        CampaignListItemView expected = toListItemView(toDto(request));
        expected.setId(actual.getId());

        assertThat(actual, is(samePropertyValuesAs(expected)));
    }
}
