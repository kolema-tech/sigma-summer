package com.sigma.aliyunvod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;

public class Test {

    public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient client) throws Exception {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId("8beb510ca4a249e0ac21a4f9773e9ee2");
        return client.getAcsResponse(request);
    }

    // 请求示例
    public static void main(String[] argv) throws ClientException {
        DefaultAcsClient client = initVodClient("LTAIoqeYx8J0I3Sn", "FDd0kOuFdoZQrArUoTwwbBSUlWZcE5");
        RefreshUploadVideoResponse response = new RefreshUploadVideoResponse();
        try {
            response = refreshUploadVideo(client);
            System.out.print("UploadAddress = " + response.getUploadAddress() + "\n");
            System.out.print("UploadAuth = " + response.getUploadAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

}
