//package com.sigma.tran;
//
//import com.google.common.base.Preconditions;
//import com.sigma.tran.domain.entity.MessageSubscriber;
//import com.sigma.tran.domain.repository.MessageSubscriberRepository;
//import com.sigma.tran.domain.valueobject.MessageStatus;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @author zhenpeng
// */
//@Slf4j
//public class AutoMessageConsumer {
//
//    @Autowired
//    MessageSubscriberRepository messageSubscriberRepository;
//
//    @Transactional(rollbackFor = Exception.class)
//    public void persistAndHandleMessage(String businessType, String payload, String guid) {
//        Preconditions.checkNotNull(businessType);
//        Preconditions.checkNotNull(payload);
//        Preconditions.checkNotNull(guid);
//        final MessageSubscriber subscriber = new MessageSubscriber();
//        subscriber.setBusinessType(businessType);
//        subscriber.setPayload(payload);
//        subscriber.setMessageStatus(MessageStatus.NEW);
//
//        messageSubscriberRepository.save(subscriber);
//    }
//}
