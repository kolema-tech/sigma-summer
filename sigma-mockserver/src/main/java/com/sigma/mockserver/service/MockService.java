package com.sigma.mockserver.service;


import com.sigma.mockserver.aspects.SigmaMock;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
public interface MockService {
    Object getValue(SigmaMock sigmaMock);
}
