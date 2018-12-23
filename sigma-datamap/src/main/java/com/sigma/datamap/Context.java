package com.sigma.datamap;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.var;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
@Getter
@Setter
public class Context {

    /**
     * 是否重建
     *
     */
    private boolean rebuild = false;

    private String id;

    private Object input;

    private Object output;

    public <TInput,TOutput> DataMapMessage<TInput,TOutput> read(){
        return null;
    }

    /**
     * 保存
     * @param input
     * @param output
     */
    public void saveMessage(Object input, Object output){
        var msg = DataMapMessage.builder()
                .id(getId())
                .request(input)
                .request(output)
                .build().toMessage();

    }
}
