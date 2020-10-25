package com.riotgame.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ReponseSummary {

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "record count")
    private long count;

    @ApiModelProperty(value = "min reponse time")
    private double min;

    @ApiModelProperty(value = "max reponse time")
    private double max;

    @ApiModelProperty(value = "tp90")
    private double tp90;

    @ApiModelProperty(value = "tp95")
    private double tp95;

    @ApiModelProperty(value = "tp99")
    private double tp99;
}
