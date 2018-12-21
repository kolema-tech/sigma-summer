package com.sigma.web.interceptor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-11:23
 * desc: 執行時間
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecutedTimeMetrics {
    /**
     * 获取开始时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime startDateTime;

    /**
     * 获取结束时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime endDateTime;

    /**
     * API 名稱
     */
    private String apiName;

    /**
     * 異常信息
     */
    private Exception exception;

    /**
     * 構造器
     *
     * @param startDateTime 開始時間
     * @param endDateTime   結束時間
     */
    public ExecutedTimeMetrics(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * 获取运行时间
     *
     * @return 时间时间，单位为ms
     */
    public Long getDuration() {
        if (startDateTime == null || endDateTime == null) {
            return -1L;
        }

        return Duration.between(startDateTime, endDateTime).toMillis();
    }
}
