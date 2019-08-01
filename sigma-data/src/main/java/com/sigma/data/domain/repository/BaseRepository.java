package com.sigma.data.domain.repository;

import com.sigma.sigmacore.web.PagingRequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author zen peng.
 * @version 1.0.6
 * date-time: 2019-06-
 * desc:
 **/
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    //根据Query拿到分页对象(分页)
    Page<T> findPageByQuery(PagingRequestParam pagingRequestParam);

    /**
     * 直接执行SQL语句
     *
     * @param sql SQL语句
     * @return 列表
     */
    List<Object[]> listBySQL(String sql);

}