package com.sigma.azures.blob;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.sigma.azures.exceptions.AzureBlobNotExistException;
import com.sigma.azures.exceptions.AzureConnectionException;
import com.sigma.azures.exceptions.AzureFileUploadException;
import com.sigma.azures.exceptions.AzureStorageException;
import lombok.experimental.var;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-23:45
 * desc:
 **/
public class AzureBlobUtil {

    /**
     * 获取Blob访问
     *
     * @param blobRequestParameter 请求参数
     * @return 访问器
     */
    public static CloudBlockBlob getCloudBlockBlob(BlobRequestParameter blobRequestParameter) {
        try {
            var storageAccount = CloudStorageAccount.parse(blobRequestParameter.getConnectionString());
            var blobClient = storageAccount.createCloudBlobClient();
            var container = blobClient.getContainerReference(blobRequestParameter.getContainer());

            return container.getBlockBlobReference(blobRequestParameter.getPath());
        } catch (Exception ex) {
            throw new AzureConnectionException(ex);
        }
    }

    /**
     * 删除文件
     *
     * @param blobRequestParameter 请求参数
     */
    public static void delete(BlobRequestParameter blobRequestParameter) {

        var blob = getCloudBlockBlob(blobRequestParameter);
        try {
            if (blob.exists()) {
                blob.delete();
            }
        } catch (StorageException ex) {
            throw new AzureStorageException(ex).withArguments(ex.getErrorCode(), ex.getHttpStatusCode());
        }
    }

    /**
     * 上传文件
     *
     * @param blobRequestParameter 请求参数
     * @param localPath            本地文件全路径
     * @return 上传的链接
     */
    public static String upload(BlobRequestParameter blobRequestParameter, String localPath) {

        try {
            var blob = getCloudBlockBlob(blobRequestParameter);
            blob.uploadFromFile(localPath);
            return blob.getStorageUri().getPrimaryUri().toString();
        } catch (StorageException ex) {
            throw new AzureStorageException(ex).withArguments(ex.getErrorCode(), ex.getHttpStatusCode());
        } catch (IOException ex) {
            throw new AzureFileUploadException(ex).withArguments(localPath);
        }
    }

    /**
     * 下载文件
     *
     * @param blobRequestParameter 请求参数
     * @return 下载的流
     */
    public static ByteArrayOutputStream download(BlobRequestParameter blobRequestParameter) {

        try {
            var blob = getCloudBlockBlob(blobRequestParameter);

            if (!blob.exists()) {
                throw new AzureBlobNotExistException().withArguments(blobRequestParameter.getPath());
            }

            var byteArrayOutputStream = new ByteArrayOutputStream();
            blob.download(byteArrayOutputStream);

            return byteArrayOutputStream;

        } catch (StorageException ex) {
            throw new AzureStorageException(ex).withArguments(ex.getErrorCode(), ex.getHttpStatusCode());
        }
    }
}
