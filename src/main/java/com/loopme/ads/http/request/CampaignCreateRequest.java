package com.loopme.ads.http.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ApiModel(value = "CampaignCreateRequest", description = "Create campaign")
public class CampaignCreateRequest {

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

    @ApiModelProperty(value = "campaign end date", required = true)
    @NotEmpty
    private List<Integer> advertisements;

    @AssertTrue(message = "End date should be greater than start date")
    @JsonIgnore
    public boolean isEndDateGreaterStartDate() {
        return endDate.isAfter(startDate);
    }
}

