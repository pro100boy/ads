package com.loopme.ads.http.request;

import com.loopme.ads.constant.Platform;
import com.loopme.ads.constant.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "AdvertisementUpdateRequest", description = "Update advertisement")
public class AdvertisementUpdateRequest {

    @ApiModelProperty(value = "advertisement name", required = true)
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @ApiModelProperty(value = "advertisement status", required = true)
    @NotNull
    private Status status;

    @ApiModelProperty(value = "platform names", required = true)
    @NotEmpty
    private List<Platform> platforms;

    @ApiModelProperty(value = "related url", required = true)
    @NotBlank
    private String assetUrl;
}
