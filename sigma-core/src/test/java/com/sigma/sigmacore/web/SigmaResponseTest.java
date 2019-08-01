package com.sigma.sigmacore.web;

import lombok.experimental.var;
import org.junit.Test;

public class SigmaResponseTest {

    @Test
    public void test() {

        var response = SigmaResponse.builder()
                .withCode("23423")
                .withMessage("successful!")
                .withData("data")
                .build();

        assert "SigmaResponse(header=SigmaResponseHeader(code=23423, message=successful!), data=data)".equals(response.toString());
    }
}