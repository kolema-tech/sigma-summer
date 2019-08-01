package com.sigma.sigmacore.web;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/12/2-16:26
 * desc:
 **/
public class SigmaResponseBuilder {

    private SigmaResponse sigmaResponse;

    public SigmaResponseBuilder(SigmaResponse sigmaResponse) {
        this.sigmaResponse = sigmaResponse;
    }

    public SigmaResponseBuilder withCode(String code) {
        if (sigmaResponse.getHeader() == null) {
            sigmaResponse.setHeader(new SigmaResponseHeader("", ""));
        }

        sigmaResponse.getHeader().setCode(code);

        return this;
    }

    /**
     * 帶上消息
     *
     * @param message 消息
     * @return
     */
    public SigmaResponseBuilder withMessage(String message) {
        if (sigmaResponse.getHeader() == null) {
            sigmaResponse.setHeader(new SigmaResponseHeader("", ""));
        }

        sigmaResponse.getHeader().setMessage(message);

        return this;
    }

    /**
     * 帶上數據
     *
     * @param data 數據
     * @return
     */
    public SigmaResponseBuilder withData(Object data) {

        sigmaResponse.setData(data);

        return this;
    }

    public SigmaResponse build() {

        return sigmaResponse;
    }
}
