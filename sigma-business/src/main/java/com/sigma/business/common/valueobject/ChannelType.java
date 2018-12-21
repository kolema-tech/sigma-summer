//package com.sigma.business.common.valueobject;
//
//import org.springframework.util.StringUtils;
//
//import javax.persistence.AttributeConverter;
//
///**
// * @author zen peng.
// * @version 1.0
// * date-time: 2018/6/4-9:31
// * desc: 渠道類型
// **/
//public enum ChannelType {
//    /**
//     * APP
//     */
//    APP,
//    /**
//     * H5
//     */
//    H5,
//    /**
//     * WEB
//     */
//    WEB,
//    /**
//     * 線下
//     */
//    OFF_LINE,
//
//    /**
//     * 2b
//     */
//    TO_B,
//
//    /**
//     * 2c
//     */
//    TO_C;
//
//    /**
//     * @author zen peng.
//     * @version 1.0
//     * date-time: 2018/6/4-9:34
//     * desc: 渠道的JPA轉換. null表示為空
//     **/
//    @javax.persistence.Converter(autoApply = true)
//    public static class ChannelTypeNameConverter implements AttributeConverter<ChannelType, String> {
//        @Override
//        public String convertToDatabaseColumn(ChannelType channelType) {
//            if (channelType == null) {
//                return null;
//            }
//
//            return channelType.name();
//        }
//
//        @Override
//        public ChannelType convertToEntityAttribute(String s) {
//            if (StringUtils.hasLength(s)) {
//                return ChannelType.valueOf(s);
//            }
//            return null;
//        }
//    }
//}