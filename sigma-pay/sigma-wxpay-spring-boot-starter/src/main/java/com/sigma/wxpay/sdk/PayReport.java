package com.sigma.wxpay.sdk;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc: 支付上报
 **/
@Slf4j
public class PayReport {

    /**
     * 上报地址
     */
    private static final String REPORT_URL = "http://report.mch.weixin.qq.com/wxpay/report/default";
    private static final int DEFAULT_CONNECT_TIMEOUT_MS = 6 * 1000;
    private static final int DEFAULT_READ_TIMEOUT_MS = 8 * 1000;
    private volatile static PayReport INSTANCE;
    private LinkedBlockingQueue<String> reportMsgQueue = null;
    private BasePayConfig config;
    private ExecutorService executorService;

    private PayReport(final BasePayConfig config) {
        this.config = config;
        reportMsgQueue = new LinkedBlockingQueue<String>(config.getReportQueueMaxSize());

        // 添加处理线程
        executorService = Executors.newFixedThreadPool(config.getReportWorkerNum(), r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        });

        if (config.shouldAutoReport()) {
            log.info("report worker num: {}", config.getReportWorkerNum());
            for (int i = 0; i < config.getReportWorkerNum(); ++i) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            // 先用 take 获取数据
                            try {
                                StringBuffer sb = new StringBuffer();
                                String firstMsg = reportMsgQueue.take();
                                log.info("get first report msg: {}", firstMsg);
                                String msg = null;
                                sb.append(firstMsg); //会阻塞至有消息
                                int remainNum = config.getReportBatchSize() - 1;
                                for (int j = 0; j < remainNum; ++j) {
                                    log.info("try get remain report msg");
                                    // msg = reportMsgQueue.poll();  // 不阻塞了
                                    msg = reportMsgQueue.take();
                                    log.info("get remain report msg: {}", msg);
                                    if (msg == null) {
                                        break;
                                    } else {
                                        sb.append("\n");
                                        sb.append(msg);
                                    }
                                }
                                // 上报
                                PayReport.httpRequest(sb.toString(), DEFAULT_CONNECT_TIMEOUT_MS, DEFAULT_READ_TIMEOUT_MS);
                            } catch (Exception ex) {
                                log.warn("report fail. reason: {}", ex.getMessage());
                            }
                        }
                    }
                });
            }
        }

    }

    /**
     * 单例，双重校验，请在 JDK 1.5及更高版本中使用
     *
     * @param config 配置
     * @return 上报服务
     */
    public static PayReport getInstance(BasePayConfig config) {
        if (INSTANCE == null) {
            synchronized (PayReport.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PayReport(config);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * http 请求
     *
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws Exception
     */
    private static String httpRequest(String data, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        BasicHttpClientConnectionManager connManager;
        connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );
        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpPost httpPost = new HttpPost(REPORT_URL);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", PayConstants.USER_AGENT);
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    public void report(String uuid, long elapsedTimeMillis,
                       String firstDomain, boolean primaryDomain, int firstConnectTimeoutMillis, int firstReadTimeoutMillis,
                       boolean firstHasDnsError, boolean firstHasConnectTimeout, boolean firstHasReadTimeout) {
        long currentTimestamp = PayUtil.getCurrentTimestamp();
        ReportInfo reportInfo = new ReportInfo(uuid, currentTimestamp, elapsedTimeMillis,
                firstDomain, primaryDomain, firstConnectTimeoutMillis, firstReadTimeoutMillis,
                firstHasDnsError, firstHasConnectTimeout, firstHasReadTimeout);
        String data = reportInfo.toLineString(config.getKey());
        log.info("report {}", data);
        if (data != null) {
            reportMsgQueue.offer(data);
        }
    }

    @ToString
    public static class ReportInfo {

        /**
         * 布尔变量使用int。0为false， 1为true。
         */

        // 基本信息
        private String version = "v1";
        private String sdk = PayConstants.WXPAYSDK_VERSION;
        private String uuid;  // 交易的标识
        private long timestamp;   // 上报时的时间戳，单位秒
        private long elapsedTimeMillis; // 耗时，单位 毫秒

        // 针对主域名
        private String firstDomain;  // 第1次请求的域名
        private boolean primaryDomain; //是否主域名
        private int firstConnectTimeoutMillis;  // 第1次请求设置的连接超时时间，单位 毫秒
        private int firstReadTimeoutMillis;  // 第1次请求设置的读写超时时间，单位 毫秒
        private int firstHasDnsError;  // 第1次请求是否出现dns问题
        private int firstHasConnectTimeout; // 第1次请求是否出现连接超时
        private int firstHasReadTimeout; // 第1次请求是否出现连接超时

        public ReportInfo(String uuid, long timestamp, long elapsedTimeMillis, String firstDomain, boolean primaryDomain, int firstConnectTimeoutMillis, int firstReadTimeoutMillis, boolean firstHasDnsError, boolean firstHasConnectTimeout, boolean firstHasReadTimeout) {
            this.uuid = uuid;
            this.timestamp = timestamp;
            this.elapsedTimeMillis = elapsedTimeMillis;
            this.firstDomain = firstDomain;
            this.primaryDomain = primaryDomain;
            this.firstConnectTimeoutMillis = firstConnectTimeoutMillis;
            this.firstReadTimeoutMillis = firstReadTimeoutMillis;
            this.firstHasDnsError = firstHasDnsError ? 1 : 0;
            this.firstHasConnectTimeout = firstHasConnectTimeout ? 1 : 0;
            this.firstHasReadTimeout = firstHasReadTimeout ? 1 : 0;
        }

        /**
         * 转换成 csv 格式
         *
         * @return 签名字符串
         */
        public String toLineString(String key) {
            String separator = ",";
            Object[] objects = new Object[]{
                    version, sdk, uuid, timestamp, elapsedTimeMillis,
                    firstDomain, primaryDomain, firstConnectTimeoutMillis, firstReadTimeoutMillis,
                    firstHasDnsError, firstHasConnectTimeout, firstHasReadTimeout
            };
            StringBuilder sb = new StringBuilder();
            for (Object obj : objects) {
                sb.append(obj).append(separator);
            }
            try {
                String sign = PayUtil.hmacsha256(sb.toString(), key);
                sb.append(sign);
                return sb.toString();
            } catch (Exception ex) {
                return null;
            }

        }

    }

}
