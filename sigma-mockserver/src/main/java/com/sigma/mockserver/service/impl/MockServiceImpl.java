package com.sigma.mockserver.service.impl;

import com.sigma.mockserver.aspects.SigmaMock;
import com.sigma.mockserver.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
@Service
public class MockServiceImpl implements MockService {

    @Override
    public Object getValue(SigmaMock sigmaMock) {

        //TODO:
        return null;
    }
}
