//package com.sigma.business.payment.entity;
//
//import com.sigma.business.common.valueobject.ChannelType;
//import com.sigma.business.payment.valueobject.AmountType;
//import com.sigma.business.payment.valueobject.PayOrderStatus;
//import com.sigma.business.payment.valueobject.SubmitAction;
//import com.sigma.data.domain.BaseDomain;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.Embedded;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import java.time.ZonedDateTime;
//
///**
// * @author zen peng.
// * @version 1.0
// * date-time: 2018/6/8-16:22
// * desc: 支付訂單
// **/
//@Entity
//@Getter
//@Setter
//public class PayOrder extends BaseDomain {
//    /**
//     * 支付訂單號，唯一
//     */
//    private String payOrderId;
//
//    /**
//     * 訂單來源
//     */
//    private ChannelType channelType;
//
//    /**
//     * 金額
//     */
//    @Embedded
//    private AmountType amountType;
//
//    /**
//     * 支付狀態
//     */
//    @Enumerated(EnumType.STRING)
//    private PayOrderStatus status;
//
//    /**
//     * 會員號，訂單所屬會員
//     */
//    private Integer memberId;
//
//    /**
//     * 業務系統訂單號
//     */
//    private String orderId;
//
//    /**
//     * 提交動作
//     */
//    private SubmitAction submitAction;
//
//    /**
//     * 過期時間
//     */
//    private ZonedDateTime expiryTime;
//}
