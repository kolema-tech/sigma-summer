package com.sigma.sigmacore.web;

/**
 * @author huston.peng
 * @version 1.0.4
 * date-time: 2019/4/10-15:35
 * desc:
 **/
public class SigmaPagingResponseBuilder {

    private SigmaPagingResponse sigmaPagingResponse;

    public SigmaPagingResponseBuilder(SigmaPagingResponse sigmaPagingResponse) {
        this.sigmaPagingResponse = sigmaPagingResponse;
    }

    public SigmaPagingResponseBuilder withCode(String code) {
        if (sigmaPagingResponse.getHeader() == null) {
            sigmaPagingResponse.setHeader(new SigmaResponseHeader("", ""));
        }

        sigmaPagingResponse.getHeader().setCode(code);

        return this;
    }

    /**
     * 帶上消息
     *
     * @param message 消息
     * @return
     */
    public SigmaPagingResponseBuilder withMessage(String message) {
        if (sigmaPagingResponse.getHeader() == null) {
            sigmaPagingResponse.setHeader(new SigmaResponseHeader("", ""));
        }

        sigmaPagingResponse.getHeader().setMessage(message);

        return this;
    }

    public SigmaPagingResponseBuilder withData(PagingResponseParam pagingResponseParam) {

        sigmaPagingResponse.setData(pagingResponseParam);

        return this;
    }

    public SigmaPagingResponse build() {

        return sigmaPagingResponse;
    }
}
