//package com.sigma.tran.domain.repository;
//
//import com.sigma.tran.domain.entity.MessagePublisher;
//import com.sigma.tran.domain.entity.MessageSubscriber;
//import com.sigma.tran.domain.valueobject.MessageStatus;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * @author zen peng.
// * @version 1.0
// * date-time: 2018/6/7-15:45
// * desc: 消息訂閱倉儲
// **/
//@Repository
//public interface MessageSubscriberRepository extends CrudRepository<MessageSubscriber, Long> {
//    /**
//     * 選擇前100條發送
//     *
//     * @param messageStatus
//     * @return
//     */
//    List<MessagePublisher> findTop100ByMessageStatus(MessageStatus messageStatus);
//}
