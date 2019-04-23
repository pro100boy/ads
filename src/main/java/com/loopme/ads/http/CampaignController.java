package com.loopme.ads.http;

import com.loopme.ads.constant.CampaignSort;
import com.loopme.ads.constant.SearchOrder;
import com.loopme.ads.constant.Status;
import com.loopme.ads.domain.Campaign;
import com.loopme.ads.dto.CampaignDto;
import com.loopme.ads.dto.CampaignListItemDto;
import com.loopme.ads.http.request.CampaignCreateRequest;
import com.loopme.ads.http.request.CampaignUpdateRequest;
import com.loopme.ads.http.request.ListQuery;
import com.loopme.ads.service.CampaignService;
import com.loopme.ads.view.CampaignItemView;
import com.loopme.ads.view.CampaignListItemView;
import com.loopme.ads.view.CampaignView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.loopme.ads.http.API.Campaign.*;
import static com.loopme.ads.http.API.ROOT_PATH_V1;
import static com.loopme.ads.http.mapper.CampaignMapper.toDto;
import static com.loopme.ads.http.mapper.CampaignMapper.toView;
import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;
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

    @ApiOperation(value = "create campaign", response = CampaignItemView.class)
    @ApiResponse(code = 201, message = "Campaign was created successfully")
    @PostMapping(value = PATH, produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CampaignItemView> create(@RequestBody @Valid CampaignCreateRequest request) {
        log.info("create advertisement with params {}", request);

        CampaignDto campaign = toDto(request);
        int id = service.create(campaign);
        CampaignItemView view = toView(campaign);
        view.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(view);
    }

    @ApiOperation(value = "search campaigns", response = CampaignListItemView.class, responseContainer = "List")
    @GetMapping(value = LIST, produces = APPLICATION_JSON_UTF8_VALUE)
    public List<CampaignListItemView> search(@ApiParam(name = "search", value = "search parameter") @RequestParam(required = false) String search,
                                             @ApiParam(name = "status", value = "status parameter") @RequestParam(required = false) String status,
                                             @ApiParam(name = "sort", value = "sort by parameter", defaultValue = "name") @RequestParam(required = false) String sort,
                                             @ApiParam(name = "order", value = "sort order: ASC or DESC", defaultValue = "ASC") @RequestParam(required = false) String order,
                                             @ApiParam(name = "offset", value = "offset", defaultValue = "0") @RequestParam(required = false) String offset,
                                             @ApiParam(name = "limit", value = "campaigns count per page", defaultValue = "10") @RequestParam(required = false) String limit) {

        ListQuery queryParams = ListQuery.builder()
                .search(StringUtils.isEmpty(search) ? null : search.trim())
                .status(isNull(status) ? null : Status.valueOf(status.toUpperCase()))
                .sort(isNull(sort) ? CampaignSort.NAME : CampaignSort.valueOf(sort.toUpperCase()))
                .order(isNull(order) ? SearchOrder.ASC : SearchOrder.valueOf(order.toUpperCase()))
                .offset(isNull(offset) ? 0 : parseInt(offset))
                .limit(isNull(limit) ? 10 : parseInt(limit))
                .build();

        log.info("search campaign by params: {}", queryParams);

        List<CampaignListItemDto> dtoList = service.search(queryParams);
        return toView(dtoList);
    }

    @ApiOperation(value = "update specify campaign", response = CampaignItemView.class)
    @PutMapping(value = CONCRETE, produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public CampaignItemView update(@PathVariable int id, @RequestBody @Valid CampaignUpdateRequest request) {
        log.info("update campaign id {} with params {}", id, request);

        CampaignDto campaign = toDto(request);
        service.update(id, campaign);
        CampaignItemView view = toView(campaign);
        view.setId(id);
        return view;
    }
}
