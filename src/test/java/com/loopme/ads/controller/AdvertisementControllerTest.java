package com.loopme.ads.controller;

import com.loopme.ads.http.request.AdvertisementCreateRequest;
import com.loopme.ads.http.request.AdvertisementUpdateRequest;
import com.loopme.ads.view.AdvertisementView;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.loopme.ads.AdsTestData.*;
import static com.loopme.ads.http.API.Advertisement.CONCRETE;
import static com.loopme.ads.http.API.Advertisement.PATH;
import static com.loopme.ads.http.API.ROOT_PATH_V1;
import static com.loopme.ads.http.mapper.AdvertisementMapper.toEntity;
import static com.loopme.ads.http.mapper.AdvertisementMapper.toView;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdvertisementControllerTest extends AbstractControllerTest {

    @Test
    public void should_get() throws Exception {
        AdvertisementView expected = adsExpectedView;

        MvcResult mvcResult = mockMvc.perform(get(ROOT_PATH_V1 + CONCRETE, 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();

        AdvertisementView actual = getGson().fromJson(
                mvcResult.getResponse().getContentAsString(), AdvertisementView.class);
        assertThat(actual, is(samePropertyValuesAs(expected)));
    }

    @Test
    public void should_delete_by_id() throws Exception {
        mockMvc.perform(delete(ROOT_PATH_V1 + CONCRETE, 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(get(ROOT_PATH_V1 + CONCRETE, 3))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_create() throws Exception {
        AdvertisementCreateRequest request = adsCreateRequest;

        MvcResult mvcResult = mockMvc.perform(
                post(ROOT_PATH_V1 + PATH)
                        .contentType(APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();

        AdvertisementView actual = getGson().fromJson(
                mvcResult.getResponse().getContentAsString(), AdvertisementView.class);
        assertTrue(actual.getId()>0);

        AdvertisementView expected = toView(toEntity(request));
        expected.setId(actual.getId());

        assertThat(actual, is(samePropertyValuesAs(expected)));
    }

    @Test
    public void should_get_not_found_status() throws Exception {
        mockMvc.perform(get(ROOT_PATH_V1 + CONCRETE, 30))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print());

        mockMvc.perform(delete(ROOT_PATH_V1 + CONCRETE, 30))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print());

        AdvertisementUpdateRequest request = adsUpdateRequest;

        mockMvc.perform(
                put(ROOT_PATH_V1 + CONCRETE, 30)
                        .contentType(APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print());
    }

    @Test
    public void should_get_bad_request_status() throws Exception {
        mockMvc.perform(get(ROOT_PATH_V1 + CONCRETE, "a"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print());

        mockMvc.perform(
                put(ROOT_PATH_V1 + CONCRETE, 3)
                        .contentType(APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(new AdvertisementUpdateRequest())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print());

        mockMvc.perform(
                post(ROOT_PATH_V1 + PATH, 3)
                        .contentType(APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(new AdvertisementCreateRequest())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print());
    }

    @Test
    public void should_update() throws Exception {
        AdvertisementView expected = adsExpectedUpdatedView;

        AdvertisementUpdateRequest request = adsUpdateRequest;

        MvcResult mvcResult = mockMvc.perform(
                put(ROOT_PATH_V1 + CONCRETE, 3)
                        .contentType(APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();

        AdvertisementView actual = getGson().fromJson(
                mvcResult.getResponse().getContentAsString(), AdvertisementView.class);

        assertThat(actual, is(samePropertyValuesAs(expected)));
    }

}
