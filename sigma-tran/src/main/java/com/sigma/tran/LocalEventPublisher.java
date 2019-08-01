//package com.sigma.tran;
//
//import com.google.common.base.Charsets;
//import com.google.common.base.Preconditions;
//import com.sigma.web.BeanValidator;
//import com.sigma.sigmacore.utils.JsonUtils;
//import com.sigma.tran.domain.entity.MessagePublisher;
//import com.sigma.tran.domain.repository.MessagePublisherRepository;
//import com.sigma.tran.domain.valueobject.MessageStatus;
//import lombok.experimental.var;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.UUID;
//
///**
// * @author zen peng.
// * @version 1.0
// * date-time: 2018/6/12-10:10
// * desc： 本地時間表發送端
// **/
//@Component
//@Slf4j
//public class LocalEventPublisher {
//
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    MessagePublisherRepository messagePublisherRepository;
//
//    @PostConstruct
//    public void init() {
//        rabbitTemplate.setReturnCallback(new LocalEventReturnCallback());
//        rabbitTemplate.setConfirmCallback(new LocalEventConfirmCallback());
//    }
//
//
//    /**
//     * 将消息保存在DB
//     *
//     * @param payload         數據
//     * @param businessType    業務類型
//     * @param messageExchange Broker
//     * @return
//     * @throws IOException
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public long persistPublishMessage(MessageExchange messageExchange, Object payload, String businessType) throws IOException {
//
//        Preconditions.checkNotNull(payload);
//        Preconditions.checkNotNull(businessType);
//
//        var publisher = new MessagePublisher();
//        publisher.setMessageStatus(MessageStatus.NEW);
//        publisher.setGuid(UUID.randomUUID().toString());
//        publisher.setPayload(JsonUtils.serialize(payload));
//        publisher.setBusinessType(businessType);
//        publisher.setExchange(messageExchange.getExchange());
//        publisher.setRoutingKey(messageExchange.getRoutingKey());
//        BeanValidator.validate(publisher);
//
//        return messagePublisherRepository.save(publisher).getId();
//    }
//
//    /**
//     * 发送到Broker
//     *
//     * @param messagePublisher 消息體
//     * @param messageExchange  Broker
//     * @param correlationData  關聯數據
//     */
//    public void publish(MessagePublisher messagePublisher, MessageExchange messageExchange, CorrelationData correlationData) {
//        Preconditions.checkNotNull(messagePublisher);
//        Preconditions.checkNotNull(messageExchange);
//
//        BeanValidator.validate(messagePublisher);
//
//        rabbitTemplate.convertAndSend(messageExchange.getExchange(), messageExchange.getRoutingKey(), messagePublisher, correlationData);
//    }
//
//    @Scheduled(fixedRate = 500L)
//    public void sendStatusNew() {
//        var list = messagePublisherRepository.findTop100ByMessageStatus(MessageStatus.NEW);
//        list.forEach(item -> {
//
//            item.setMessageStatus(MessageStatus.PENDING);
//            messagePublisherRepository.save(item);
//
//            publish(item, MessageExchange.create(item.getExchange(), item.getRoutingKey()), new CorrelationData(item.getGuid()));
//        });
//    }
//
//    /**
//     * @author zen peng.
//     * @version 1.0
//     * date-time: 2018/6/12-10:22
//     * desc: 本地事件ReturnCallback
//     **/
//    private class LocalEventReturnCallback implements RabbitTemplate.ReturnCallback {
//
//        @Override
//        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//
//            var failedMessage = new String(message.getBody(), Charsets.UTF_8);
//
//            String guid = null;
//            try {
//                guid = JsonUtils.getMapper().readTree(failedMessage).get("guid").asText();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            var publisher = messagePublisherRepository.findFirstByGuid(guid);
//            if (publisher != null) {
//                if (MessageStatus.NOT_FOUND.name().equals(replyText)) {
//                    publisher.setMessageStatus(MessageStatus.NO_ROUTE);
//                } else {
//                    publisher.setMessageStatus(MessageStatus.ERROR);
//                }
//
//                messagePublisherRepository.save(publisher);
//            }
//        }
//    }
//
//    /**
//     * @author zen peng.
//     * @version 1.0
//     * date-time: 2018/6/12-10:18
//     * desc: 本地事務的ConfirmCallback
//     **/
//    private class LocalEventConfirmCallback implements RabbitTemplate.ConfirmCallback {
//
//        @Override
//        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//            var id = correlationData.getId();
//            var entity = messagePublisherRepository.findFirstByGuidAndMessageStatus(id, MessageStatus.PENDING);
//            if (entity != null) {
//                if (ack) {
//                    entity.setMessageStatus(MessageStatus.DONE);
//                    messagePublisherRepository.save(entity);
//                } else {
//                    // 打开mandatory之后，ack为false的情况就是没有找到exchange
//                    log.error("message has failed to found a proper exchange which local id is {}. cause: {}", id, cause);
//
//                    entity.setMessageStatus(MessageStatus.NOT_FOUND);
//                    messagePublisherRepository.save(entity);
//                }
//            }
//        }
//    }
//}
