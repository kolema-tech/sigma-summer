package com.sigma.aliyunstarter;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc:
 **/
@Slf4j
public class AliOssService {

    @Getter
    @Setter
    private AliOssProperties ossProperties;

    public AliOssService(AliOssProperties aliOssProperties) {
        this.ossProperties = aliOssProperties;
    }

    /**
     * 获取文件全地址
     *
     * @param bucket bucket
     * @param key    文件名
     * @return 文件Url.
     */
    public String getOssUrl(String bucket, String key) {
        return MessageFormat.format("https://{0}.{1}/{2}", bucket, ossProperties.getEndPoint(), key);
    }

    /**
     * 获取文件地址
     *
     * @param bucket  bucket
     * @param key     文件名
     * @param seconds 过期的秒数
     * @return URL连接
     */
    public URL getOssUrl(String bucket, String key, Integer seconds) {
        Date expiration = new Date(System.currentTimeMillis() + seconds * 1000);
        return getClient().generatePresignedUrl(bucket, key, expiration);
    }

    /**
     * 上传文件
     *
     * @param bucket      bucket
     * @param key         文件名
     * @param inputStream 文件流
     */
    public PutObjectResult upload(String bucket, String key, InputStream inputStream) {

        OSS client = getClient();

        try {

            var putObjectRequest = new PutObjectRequest(bucket, key, inputStream);

            return client.putObject(putObjectRequest);

        } catch (OSSException oe) {
            log.error("Caught an OSSException", oe);
        } catch (ClientException ce) {
            log.error("Caught an ClientException", ce);
        } finally {
            client.shutdown();
        }

        return null;
    }

    public OSS getClient() {
        return new OSSClientBuilder()
                .build(ossProperties.getEndPoint(),
                        ossProperties.getAccessKeyId(),
                        ossProperties.getSecret());
    }
}
