package com.sigma.sigmadatamybatiscore.context;

import com.sigma.web.ThreadContextHolder;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/12/21-14:44
 * desc:
 **/
public class AuditContextHandler extends ThreadContextHolder {

    private static final String CONTEXT_KEY_USER_ID = "USERID";
    private static final String CONTEXT_KEY_USER_NAME = "USERNAME";

    public static String getUserId() {
        return getValue(CONTEXT_KEY_USER_ID, String.class);
    }

    public static void setUserId(String userId) {
        setValue(CONTEXT_KEY_USER_ID, userId);
    }

    public static String getUserName() {
        return getValue(CONTEXT_KEY_USER_NAME, String.class);
    }

    public static void setUserName(String userName) {
        setValue(CONTEXT_KEY_USER_NAME, userName);
    }
}
