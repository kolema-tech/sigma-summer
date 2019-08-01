//package com.sigma.business.payment.entity;
//
//import com.sigma.business.common.valueobject.ChannelType;
//import com.sigma.business.payment.valueobject.CreditCardInfo;
//import com.sigma.business.payment.valueobject.PayStatus;
//import com.sigma.business.payment.valueobject.PaymentResponse;
//import com.sigma.data.domain.BaseDomain;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * @author zen peng.
// * @version 1.0
// * date-time: 2018/6/8-17:01
// * desc: 待扣款
// * 每一個PayOrder 對應多個Deduction.
// **/
//@Getter
//@Setter
//public class Deduction extends BaseDomain {
//
//    /**
//     * 支付訂單號
//     */
//    private String payOrderId;
//
//    /**
//     * 支付狀態，依賴于PayOrder
//     */
//    private PayStatus payStatus;
//
//    /**
//     * 支付結果
//     */
//    private PaymentResponse paymentResponse;
//
//    /**
//     * 卡信息
//     */
//    private CreditCardInfo creditCardInfo;
//
//    /**
//     * 收據頭
//     */
//    private String receiptHeader;
//
//    /**
//     * 支付網關
//     */
//    private String gateWay;
//
//    /**
//     * 支付渠道
//     */
//    private ChannelType channelType;
//}
