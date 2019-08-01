package com.sigma.samples;

import com.sigma.azures.blob.AzureBlobUtil;
import com.sigma.azures.blob.BlobRequestParameter;
import com.sigma.samples.request.AzureGetFileContentRequest;
import com.sigma.sigmacore.web.SigmaRequest;
import com.sigma.sigmacore.web.SigmaResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/21-14:51
 * desc: Azure Blob 测试
 **/
@RestController
@Api(value = "azure测试")
public class AzureController {

    @ApiOperation(value = "获取文件")
    @PostMapping(value = "/getFileContent")
    public SigmaResponse<String> getFileContent(@RequestBody SigmaRequest<AzureGetFileContentRequest> request) throws UnsupportedEncodingException {

        var boas = AzureBlobUtil.download(BlobRequestParameter.builder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=;AccountKey=;EndpointSuffix=core.chinacloudapi.cn")
                .container("cname")
                .path("filename")
                .build());

        return SigmaResponse.ok(boas.toString("utf-8"));
    }

}
