//package com.sigma.business.blacklist;
//
//import org.springframework.stereotype.Service;
//
///**
// * @author zen peng.
// * @version 1.0
// * date-time: 2018/6/8-18:06
// * desc:
// **/
//@Service
//public class BlacklistServiceImpl<T extends BaseBlackList> implements BlackListService<T> {
//
//    BlacklistRepository repository;
//
//    @Override
//    public void add(T value) {
//        repository.save(value);
//    }
//
//    @Override
//    public Boolean exist(T t) {
//        return repository.findFirstByKey(t.getKey()) != null;
//    }
//}
