package com.loopme.ads.http.request;

import com.loopme.ads.constant.CampaignSort;
import com.loopme.ads.constant.SearchOrder;
import com.loopme.ads.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListQuery {
    private String search;
    private Integer offset;
    private Integer limit;
    private Status status;
    private SearchOrder order;
    private CampaignSort sort;
}
