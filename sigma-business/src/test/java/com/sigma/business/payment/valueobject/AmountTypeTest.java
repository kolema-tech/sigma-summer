package com.sigma.business.payment.valueobject;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-15:07
 * desc: 金額類型測試
 **/
public class AmountTypeTest {

    @Test
    public void getAmount() {
        AmountType amountType = new AmountType(Currency.HKD, new BigDecimal("120"));
        Assert.assertEquals(new BigDecimal("120"), amountType.getAmount());
    }

    @Test
    public void amountTypeEquals() {
        AmountType amountType = new AmountType(Currency.HKD, new BigDecimal("120"));
        AmountType amountType2 = new AmountType(Currency.HKD, new BigDecimal("120"));

        Assert.assertEquals(amountType, amountType2);
    }

    @Test
    public void amountTypeNotEquals() {
        AmountType amountType = new AmountType(Currency.HKD, new BigDecimal("120"));
        AmountType amountType2 = new AmountType(Currency.TWD, new BigDecimal("120"));

        Assert.assertNotEquals(amountType, amountType2);
    }
}