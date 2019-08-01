package com.sigma.sigmacore.validation;

import com.sigma.sigmacore.Person;
import lombok.experimental.var;
import org.springframework.stereotype.Component;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-15:01
 * desc:
 **/
@Component
public class PersonValidation implements Validation<Person> {
    @Override
    public ValidationResult validate(Person context) {

        var result = new ValidationResult();
        result.setSuccess(true);

        if (context.getName().equals("pz")) {
            result.setCode("V002");
            result.setMessage("大牛是你嗎！");
            result.setSuccess(false);
        }

        return result;
    }
}
