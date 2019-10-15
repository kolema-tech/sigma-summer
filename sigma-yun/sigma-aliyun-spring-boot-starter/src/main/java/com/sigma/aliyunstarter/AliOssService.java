package com.sigma.aliyunstarter;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.var;

import java.io.InputStream;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc:
 **/
public class AliOssService {

    @Getter
    @Setter
    private AliOssProperties ossProperties;

    public AliOssService(AliOssProperties aliOssProperties) {
        this.ossProperties = aliOssProperties;
    }

    public String getFileName(String fileName) {
        return "https://" + ossProperties.getBucket() + "." + ossProperties.getEndPoint() + "/" + fileName;
    }

    private Bucket createBucket(String bucketName) {
        OSS client = getClient();
        Bucket bucket = client.createBucket(bucketName);
        client.shutdown();
        return bucket;
    }

    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param inputStream 文件流
     */
    public PutObjectResult upload(String fileName, InputStream inputStream) {
        OSS client = getClient();
        var putObjectRequest = new PutObjectRequest(ossProperties.getBucket(), fileName, inputStream);
        var putObjectResult = client.putObject(putObjectRequest);

        client.shutdown();

        return putObjectResult;
    }

    public OSS getClient() {
        return new OSSClientBuilder()
                .build(ossProperties.getEndPoint(),
                        ossProperties.getAccessKeyId(),
                        ossProperties.getSecret());
    }
}
