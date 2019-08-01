package com.sigma.sigmacore.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author huston.peng
 * @version 1.0.4
 * <p>
 * date-time: 2019/4/10-13:43
 * desc: 分页响应
 **/
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "分页响应参数")
public class PagingResponseParam<TPagingData> extends PagingRequestParam {

    @NotNull
    @ApiModelProperty(value = "总数")
    private Integer total;

    @NotNull
    @ApiModelProperty(value = "总的页数")
    private Integer totalPages;

    /**
     * 響應體
     */
    @ApiModelProperty(value = "分页列表")
    private List<TPagingData> pagingDatas;
}
