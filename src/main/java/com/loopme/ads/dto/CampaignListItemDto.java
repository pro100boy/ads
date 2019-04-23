package com.loopme.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CampaignListItemDto {
    private Integer id;
    private String name;
    private Integer status;
    private Integer adsCnt;
}
