//package com.sigma.business.payment.valueobject;
//
//import lombok.Getter;
//import org.springframework.util.StringUtils;
//
//import javax.persistence.AttributeConverter;
//
///**
// * @author zen peng.
// * @version 1.0
// * date-time: 2018/6/4-9:12
// * desc: https://www.currency-iso.org/dam/downloads/lists/list_one.xml
// **/
//public enum Currency {
//    /**
//     * 人民幣
//     */
//    CNY(156),
//
//    /**
//     * 美元
//     */
//    USD(840),
//
//    /**
//     * 新加坡幣
//     */
//    SGD(702),
//
//    /**
//     * 港幣
//     */
//    HKD(344),
//
//    /**
//     * 葡幣
//     */
//    MOP(446),
//
//    /**
//     * 台幣
//     */
//    TWD(901);
//
//    @Getter
//    private int code;
//
//    Currency(int code) {
//        this.code = code;
//    }
//
//    /**
//     * @author zen peng.
//     * @version 1.0
//     * date-time: 2018/6/4-9:21
//     * desc: 幣種按照名稱轉換. null表示為空
//     **/
//    @javax.persistence.Converter(autoApply = true)
//    public static class CurrencyNameConverter implements AttributeConverter<Currency, String> {
//        @Override
//        public String convertToDatabaseColumn(Currency currency) {
//            if (currency == null) {
//                return "";
//            }
//            return currency.name();
//        }
//
//        @Override
//        public Currency convertToEntityAttribute(String s) {
//
//            if (StringUtils.hasLength(s)) {
//                return Currency.valueOf(s);
//            }
//
//            return null;
//        }
//    }
//}
//
