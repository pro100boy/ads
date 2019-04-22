package com.loopme.ads.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Advertisement {
    private Integer id;
    private String name;
    private Integer status;
    private List<Integer> platforms;
    private String assetUrl;
}
