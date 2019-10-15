package com.sigma.data.domain.service;

import com.sigma.sigmacore.web.PagingRequestParam;
import com.sigma.sigmacore.web.PagingResponseParam;
import lombok.experimental.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/5/19-16:29
 * desc:
 **/
public class RepositoryUtil {
    /**
     * 构造分页结果
     *
     * @param pagingRequestParam 分页参数
     * @param page               分页查询结果
     * @param function           转换器
     * @param <TEntity>          实体
     * @param <TDto>             DTO
     * @return 分页结果
     */
    public static <TEntity, TDto> PagingResponseParam<TDto> createPagingResult(PagingRequestParam pagingRequestParam,
                                                                               Page<TEntity> page,
                                                                               Function<List<TEntity>, List<TDto>> function) {

        var result = new PagingResponseParam<TDto>();

        result.setPageIndex(pagingRequestParam.getPageIndex());
        result.setPageSize(pagingRequestParam.getPageSize());

        if (page != null) {
            result.setTotal((int) page.getTotalElements());
            result.setTotalPages(page.getTotalPages());
            result.setPagingDatas(new ArrayList<>());
            if (function != null) {
                result.setPagingDatas(function.apply(page.getContent()));
            }
        }

        return result;
    }

    /**
     * 创建分页请求
     *
     * @param pagingRequestParam 分页参数
     * @param sort               排序
     * @return Pageable
     */
    public static Pageable createPageable(PagingRequestParam pagingRequestParam, Sort sort) {
        Pageable pageable = PageRequest.of(pagingRequestParam.getPageIndex() - 1, pagingRequestParam.getPageSize(), sort);
        return pageable;
    }
}
