package com.sigma.sigmacore.validation;

import com.sigma.sigmacore.Person;
import lombok.experimental.var;
import org.springframework.stereotype.Component;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-14:59
 * desc:
 **/
@Component
public class BasicValidation implements Validation<Person> {


    @Override
    public ValidationResult validate(Person context) {

        var result = new ValidationResult();
        result.setSuccess(true);

        if (context.getAge() == 18) {
            result.setCode("V001");
            result.setMessage("年齡太小了！");
            result.setSuccess(false);
        }

        return result;
    }
}
