package com.sigma.datamap.inbuild;


import com.sigma.datamap.BaseDataFlowMap;
import com.sigma.dataredis.service.RedisService;
import lombok.experimental.var;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
public class RedisDataFlowMap<Output> extends BaseDataFlowMap<String, Output> {
    private RedisService redisService;

    public RedisDataFlowMap(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    protected Output execute(String input) {

        var val = redisService.getValue(input);
        return (Output) val;
    }
}
