package com.loopme.ads.view;

import com.loopme.ads.constant.Platform;
import com.loopme.ads.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdvertisementView {
    private Integer id;
    private String name;
    private Status status;
    private List<Platform> platforms;
    private String assetUrl;
}
