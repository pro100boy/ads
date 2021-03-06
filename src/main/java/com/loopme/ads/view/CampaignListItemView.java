package com.loopme.ads.view;

import com.loopme.ads.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CampaignListItemView {
    private Integer id;
    private String name;
    private Status status;
    private Integer adsCount;
}
