package com.loopme.ads.http.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loopme.ads.constant.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "CampaignUpdateRequest", description = "Update campaign")
public class CampaignUpdateRequest {

    @ApiModelProperty(value = "campaign name", required = true)
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @ApiModelProperty(value = "campaign start date", required = true)
    @NotNull
    private LocalDateTime startDate;

    @ApiModelProperty(value = "campaign end date", required = true)
    @NotNull
    private LocalDateTime endDate;

    @ApiModelProperty(value = "campaign status", required = true)
    @NotNull
    private Status status;

    @ApiModelProperty(value = "advertisements related to campaign", required = true)
    @NotEmpty
    private List<Integer> advertisements;

    @AssertTrue(message = "End date should be greater than start date")
    @JsonIgnore
    public boolean isEndDateGreaterStartDate() {
        return endDate.isAfter(startDate);
    }
}

