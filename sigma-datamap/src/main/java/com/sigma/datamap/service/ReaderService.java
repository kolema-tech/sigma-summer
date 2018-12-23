package com.sigma.datamap.service;


import com.sigma.dataredis.service.RedisService;
import com.sigma.rabbit.PublishService;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
@Service
public abstract class ReaderService {

    @Autowired
    RedisService redisService;

    @Autowired
    PublishService publishService;

    public Object read() {

        var query = queryCondition();

        if (query) {
            query();
        }

        do {
            query = queryCondition();
            if (query) {
                break;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);

        return readCore();
    }

    public abstract boolean queryCondition();

    public abstract void query();

    public abstract Object readCore();
}
