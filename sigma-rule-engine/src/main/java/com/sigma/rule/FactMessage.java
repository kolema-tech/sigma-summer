package com.sigma.rule;

/**
 * @author zhenpeng
 * 消息定義
 */
public interface FactMessage {

    /**
     * 獲取消息
     *
     * @return 消息
     */
    String getMessage();

    /**
     * 設置消息
     *
     * @param message 消息
     */
    void setMessage(String message);

}
