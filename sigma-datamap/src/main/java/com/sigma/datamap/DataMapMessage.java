package com.sigma.datamap;


import com.sigma.sigmacore.utils.JsonUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
@Getter
@Setter
@Builder
public class DataMapMessage<Input,Output> {

    private String id;

    private Input request;

    private Output response;

    public String toMessage(){
        try {
            return JsonUtils.serialize(this,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <TInput,TOutput> DataMapMessage<TInput,TOutput> fromMessage(String message){
        return null;
    }
}
