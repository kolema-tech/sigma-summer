package com.sigma.azures.blob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/6/16-23:40
 * desc:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlobRequestParameter {

    /**
     * 连接字符串
     */
    private String connectionString;

    /**
     * 容器
     */
    private String container;

    /**
     * 相对路径
     */
    private String path;
}
