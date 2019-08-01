package com.sigma.logging;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-23:01
 * desc:
 **/
public class SigmaLoggerMdcConstants {

    /**
     * 性能时间
     */
    public static final String DURATION = "duration";

    /**
     * 请求报文
     */
    public static final String REQ = "req";

    /**
     * 响应报文
     */
    public static final String RES = "res";

    public static final String TRACE_ID = "X-B3-TraceId";

    public static final String SPAN_ID = "X-B3-SpanId";

    public static final String PARENT_SPAN_ID = "X-B3-ParentSpanId";
}
