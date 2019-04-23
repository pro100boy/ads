package com.loopme.ads.http;

import com.loopme.ads.domain.Advertisement;
import com.loopme.ads.http.request.AdvertisementCreateRequest;
import com.loopme.ads.http.request.AdvertisementUpdateRequest;
import com.loopme.ads.service.AdvertisementService;
import com.loopme.ads.view.AdvertisementView;
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

import static com.loopme.ads.http.API.Advertisement.CONCRETE;
import static com.loopme.ads.http.API.Advertisement.PATH;
import static com.loopme.ads.http.API.ROOT_PATH_V1;
import static com.loopme.ads.http.mapper.AdvertisementMapper.toEntity;
import static com.loopme.ads.http.mapper.AdvertisementMapper.toView;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(ROOT_PATH_V1)
@RestController
@RequestMapping(ROOT_PATH_V1)
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdvertisementController {

    private final AdvertisementService service;

    @ApiOperation(value = "get specify advertisement", response = AdvertisementView.class)
    @GetMapping(value = CONCRETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public AdvertisementView get(@PathVariable int id) {
        log.info("get advertisement, id={}", id);
        Advertisement advertisement = service.get(id);
        return toView(advertisement);
    }

    @ApiOperation(value = "delete specify advertisement", response = Void.class)
    @DeleteMapping(value = CONCRETE)
    public void delete(@PathVariable int id) {
        log.info("delete advertisement, id={}", id);
        service.delete(id);
    }

    @ApiOperation(value = "create advertisement", response = AdvertisementView.class)
    @ApiResponse(code = 201, message = "Advertisement was created successfully")
    @PostMapping(value = PATH, produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AdvertisementView> create(@RequestBody @Valid AdvertisementCreateRequest request) {
        log.info("create advertisement with params {}", request);

        Advertisement advertisement = toEntity(request);
        int id = service.create(advertisement);
        advertisement.setId(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(toView(advertisement));
    }

    @ApiOperation(value = "update specify advertisement", response = AdvertisementView.class)
    @PutMapping(value = CONCRETE, produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public AdvertisementView update(@PathVariable int id, @RequestBody @Valid AdvertisementUpdateRequest request) {
        log.info("update advertisement id {} with params {}", id, request);

        Advertisement advertisement = toEntity(request);
        advertisement.setId(id);
        service.update(advertisement);

        return toView(advertisement);
    }
}
