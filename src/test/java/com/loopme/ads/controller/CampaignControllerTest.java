package com.loopme.ads.controller;

import com.google.gson.reflect.TypeToken;
import com.loopme.ads.http.request.CampaignCreateRequest;
import com.loopme.ads.http.request.CampaignUpdateRequest;
import com.loopme.ads.view.CampaignItemView;
import com.loopme.ads.view.CampaignListItemView;
import com.loopme.ads.view.CampaignView;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.loopme.ads.TestData.*;
import static com.loopme.ads.http.API.Campaign.*;
import static com.loopme.ads.http.API.ROOT_PATH_V1;
import static com.loopme.ads.http.mapper.CampaignMapper.toDto;
import static com.loopme.ads.http.mapper.CampaignMapper.toView;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
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

        CampaignItemView actual = getGson().fromJson(
                mvcResult.getResponse().getContentAsString(), CampaignItemView.class);

        assertTrue(actual.getId() > 0);

        CampaignItemView expected = toView(toDto(request));
        expected.setId(actual.getId());

        assertThat(actual, is(samePropertyValuesAs(expected)));
    }

    @Test
    public void should_search_by_default() throws Exception {
        List<CampaignListItemView> expected = allCampaigns;

        MvcResult mvcResult = mockMvc.perform(get(ROOT_PATH_V1 + LIST))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();

        List<CampaignListItemView> actual = getGson().fromJson(
                mvcResult.getResponse().getContentAsString(),
                new TypeToken<List<CampaignListItemView>>() {
                }.getType());

        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_search_by_all_params() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(ROOT_PATH_V1 + LIST)
                .param("search", "a")
                .param("offset", "1")
                //.param("status", "planned")
                .param("limit", "2")
                .param("order", "desc")
                .param("sort", "ads"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();

        List<CampaignListItemView> actual = getGson().fromJson(
                mvcResult.getResponse().getContentAsString(),
                new TypeToken<List<CampaignListItemView>>() {
                }.getType());

        assertThat(actual, contains(
                hasProperty("name", is("COLA")),
                hasProperty("name", is("Social"))
        ));
    }

    @Test
    public void should_update() throws Exception {
        CampaignUpdateRequest request = cmpUpdateRequest;

        MvcResult mvcResult = mockMvc.perform(
                put(ROOT_PATH_V1 + CONCRETE, 1)
                        .contentType(APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();

        CampaignItemView actual = getGson().fromJson(
                mvcResult.getResponse().getContentAsString(), CampaignItemView.class);
        CampaignItemView expected = toView(toDto(request));
        expected.setId(actual.getId());

        assertThat(actual, is(samePropertyValuesAs(expected)));
    }
}
