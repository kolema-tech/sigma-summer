package com.sigma.sigmacore.utils;

import org.junit.Test;

/**
 * @version 1.0
 * date-time: $CREAT_DATE$-$CREAT_TIME$
 * desc:
 * @auther zen peng.
 **/
public class AesUtilTest {

    @Test
    public void encrypt() throws Exception {
        String content = "12456";
        System.out.println(AesUtil.encrypt("12456"));
    }

    @Test
    public void decrypt() throws Exception {
        String content = "A17405AC40CA9971B2643B391AAE456E";
        System.out.println(AesUtil.decrypt("07C1362C1E7EE5778C87B515B8B098FD"));
    }
}