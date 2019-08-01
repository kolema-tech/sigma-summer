package com.sigma.data.domain.repository;

import com.sigma.sigmacore.web.PagingRequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author zen peng.
 * @version 1.0.6
 * date-time: 2019-06-
 * desc:
 **/
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    //必需要实现父类的这个构造器
    public BaseRepositoryImpl(Class domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    public Page findPageByQuery(PagingRequestParam baseQuery) {
        //第一步：拿到所有高级查询条件
        //Specification spec = baseQuery.createSpecification();
        //第二步:拿到排序的值
        //Sort sort = baseQuery.createSort();
        //第三步:根据条件查询分页数据并且返回
        Pageable pageable = new PageRequest(baseQuery.getPageIndex(), baseQuery.getPageSize());
        Page page = super.findAll(pageable);
        return page;
    }

    @Override
    public List<Object[]> listBySQL(String sql) {
        return entityManager.createNativeQuery(sql).getResultList();
    }
}