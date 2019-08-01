//package com.sigma.tran.domain.repository;
//
//import com.sigma.tran.domain.entity.MessagePublisher;
//import com.sigma.tran.domain.valueobject.MessageStatus;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * @author zen peng.
// * @version 1.0
// * date-time: 2018/6/7-10:47
// * desc: 消息發送倉儲
// **/
//@Repository
//public interface MessagePublisherRepository extends CrudRepository<MessagePublisher, Long> {
//
//    /**
//     * 選擇前100條發送
//     *
//     * @param messageStatus
//     * @return
//     */
//    List<MessagePublisher> findTop100ByMessageStatus(MessageStatus messageStatus);
//
//    /**
//     * 根據GUID和狀態查詢
//     *
//     * @param guid          GUID
//     * @param messageStatus 消息狀態
//     * @return 消息
//     */
//    MessagePublisher findFirstByGuidAndMessageStatus(String guid, MessageStatus messageStatus);
//
//    /**
//     * 根據GUID查找
//     *
//     * @param guid
//     * @return
//     */
//    MessagePublisher findFirstByGuid(String guid);
//}
