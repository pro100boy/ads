package com.loopme.ads.http;

import com.loopme.ads.domain.Campaign;
import com.loopme.ads.dto.CampaignDto;
import com.loopme.ads.http.mapper.CampaignMapper;
import com.loopme.ads.http.request.CampaignCreateRequest;
import com.loopme.ads.service.CampaignService;
import com.loopme.ads.view.CampaignListItemView;
import com.loopme.ads.view.CampaignView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.loopme.ads.http.API.Campaign.CONCRETE;
import static com.loopme.ads.http.API.Campaign.PATH;
import static com.loopme.ads.http.API.ROOT_PATH_V1;
import static com.loopme.ads.http.mapper.CampaignMapper.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(ROOT_PATH_V1)
@RestController
@RequestMapping(ROOT_PATH_V1)
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CampaignController {

    private final CampaignService service;

    @ApiOperation(value = "get specify campaign", response = CampaignView.class)
    @GetMapping(value = CONCRETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public CampaignView get(@PathVariable int id) {
        log.info("get campaign, id={}", id);
        Campaign campaign = service.get(id);
        return toView(campaign);
    }

    @ApiOperation(value = "delete specify campaign", response = Void.class)
    @DeleteMapping(value = CONCRETE)
    public void delete(@PathVariable int id) {
        log.info("delete campaign, id={}", id);
        service.delete(id);
    }

    @ApiOperation(value = "create campaign", response = CampaignListItemView.class)
    @ApiResponse(code = 201, message = "Advertisement created successfully")
    @PostMapping(value = PATH, produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CampaignListItemView> create(@RequestBody @Valid CampaignCreateRequest request) {
        log.info("create advertisement with params {}", request);

        CampaignDto campaign = toDto(request);
        int id = service.create(campaign);
        CampaignListItemView campaignListView = toListItemView(campaign);
        campaignListView.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(campaignListView);
    }
}
