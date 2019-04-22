package com.loopme.ads.view;

import com.loopme.ads.constant.Status;
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
public class CampaignListItemView {
    private Integer id;
    private String name;
    private Status status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Integer> advertisements;
}
