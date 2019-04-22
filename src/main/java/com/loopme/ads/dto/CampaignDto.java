package com.loopme.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CampaignDto {
    private String name;
    private Integer status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Integer> advertisements;
}
