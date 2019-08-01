package com.sigma.sigmacore.constant;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/6/13-21:43
 * desc:
 **/
public class SigmaResponseCodes {

    public static final String SUCCESS = "0";

    public static final String ERROR_CODE = "500";

    public static final String AUTH_ERROR_CODE = "401";

    /**
     * 实体不存在
     */
    public static final String ENTITY_NOT_EXIST = "ET000001";


    public static class Cloud {

        public static class Azure {
            /**
             * Azure连接异常
             */
            public static final String AZURE_CONN_EXCEPTION = "AZ000001";

            /**
             * Azure 存储异常
             */
            public static final String AZURE_STORAGE_EXCEPTION = "AZ000002";

            /**
             * Azure 上传文件异常
             */
            public static final String AZURE_UPLOAD_FILE_EXCEPTION = "AZ000003";

            /**
             * Azure 文件不存在
             */
            public static final String AZURE_FILE_NOT_EXIST_EXCEPTION = "AZ000004";
        }
    }
}
