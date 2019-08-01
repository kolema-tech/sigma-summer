//package com.sigma.business;
//
//import lombok.experimental.var;
//import org.springframework.util.StringUtils;
//
//import javax.validation.Constraint;
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Pattern;
//import java.lang.annotation.*;
//
///**
// * @author zen peng.
// * @version 1.0
// * date-time: 2018/6/7-14:50
// * desc: 信用卡的註解
// **/
//@Target(value = {ElementType.FIELD, ElementType.METHOD})
//@Retention(value = RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
//@Pattern(regexp = "\\d{16}")
//@NotBlank
//@Constraint(validatedBy = {CreditCard.CreditCardChecker.class})
//public @interface CreditCard {
//    String message() default "信用卡不合法！";
//
//    class CreditCardChecker implements ConstraintValidator<CreditCard, String> {
//        @Override
//        public void initialize(CreditCard constraintAnnotation) {
//        }
//
//        @Override
//        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//            if (StringUtils.hasLength(s)) {
//                var card = Long.parseLong(s);
//                checkCreditCardByLuhn(card);
//                return true;
//            }
//            return false;
//        }
//
//        Boolean checkCreditCardByLuhn(long cardNo) {
//
//            long nextDigit, sum = 0;
//            Boolean alt = false;
//
//            while (cardNo != 0) {
//                nextDigit = cardNo % 10;
//                if (alt) {
//                    nextDigit *= 2;
//                    nextDigit -= (nextDigit > 9) ? 9 : 0;
//                }
//                sum += nextDigit;
//                alt = !alt;
//                cardNo /= 10;
//            }
//
//            return (sum % 10 == 0);
//        }
//    }
//}
