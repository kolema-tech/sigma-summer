package com.sigma.business.payment.valueobject;

import com.sigma.sigmacore.exception.SigmaException;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/12/8-10:48
 * desc:
 **/
@Getter
@Setter
public class Amount implements Serializable {

    private String currency;

    private BigDecimal amount;

    private CurrencySign currencySign = CurrencySign.HK$;

    public Amount(String currencyAmount) {
        this.currency = currencyAmount.substring(0, 3);
        this.amount = new BigDecimal(currencyAmount.substring(3));
        this.currencySign = CurrencySign.getCurrencySign(this.currency);
    }

    public Amount minus(Amount target) {
        if (!target.getCurrency().equals(this.getCurrency())) {
            throw new SigmaException("幣種符合不對！");
        }
        return new Amount(this.getCurrency() + this.getAmount().subtract(target.getAmount()));
    }

    /**
     * 加上一个金额
     *
     * @param target
     * @return
     */
    public Amount plus(Amount target) {
        if (!target.getCurrency().equals(this.getCurrency())) {
            throw new SigmaException("幣種符合不對！");
        }
        return new Amount(this.getCurrency() + this.getAmount().add(target.getAmount()));
    }

    @Override
    public String toString() {
        return this.currencySign.name() + " " + this.amount;
    }

    /**
     * 除以一个金额
     *
     * @param dividend
     * @return
     */
    public Amount divide(int dividend) {
        return new Amount(this.getCurrency() + this.getAmount().divide(BigDecimal.valueOf(dividend), 2, RoundingMode.HALF_UP));
    }

    public enum CurrencySign {
        /**
         * 港幣
         */
        HK$,

        /**
         * 新幣
         */
        S$;

        public static CurrencySign getCurrencySign(String currency) {
            if ("HKD".equals(currency)) {
                return CurrencySign.HK$;
            }

            return CurrencySign.S$;
        }
    }
}
