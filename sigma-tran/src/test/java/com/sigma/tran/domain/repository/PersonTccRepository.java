package com.sigma.tran.domain.repository;

import com.sigma.tran.domain.entity.PersonTccEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-14:01
 * desc:
 **/
@Repository
public interface PersonTccRepository extends CrudRepository<PersonTccEntity, Long> {
}
