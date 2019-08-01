package com.sigma.sigmacore.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author huston.peng
 * @version 1.0.4
 * date-time: 2019/4/10-13:43
 * desc: 分页请求
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页请求参数")
public class PagingRequestParam {

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "分页索引", notes = "从1开始")
    private Integer pageIndex;
}
