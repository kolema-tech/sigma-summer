package com.sigma.scheduler.zookeeper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.var;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/12-13:18
 * desc: 節點
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZkPath {

    /**
     * 無子節點
     */
    private static final String FORMAT_SUB = "/{0}/{1}";
    /**
     * 具有子節點
     */
    private static final String FORMAT_SUBNODE = "/{0}/{1}/{2}";
    /**
     * 服務名稱
     */
    private String service;

    /**
     * 是否master
     */
    private Boolean master;
    /**
     * 子節點
     */
    private String subNode;

    /**
     * 數據
     */
    private String data;

    public static ZkPath createByString(String path) {
        var zkPath = new ZkPath();
        var arr = path.split("/");
        zkPath.setService(arr[1]);
        zkPath.setMaster("master".equals(arr[2]));
        zkPath.setSubNode(arr[3]);
        return zkPath;
    }

    /**
     * 創建cron節點 /service/self/cron
     *
     * @param service
     * @param master
     * @param cronData
     * @return
     */
    public static ZkPath createCron(String service, Boolean master, String cronData) {
        return new ZkPath(service, master, "cron", cronData);
    }

    public static ZkPath createResume(String service, Boolean master) {
        return new ZkPath(service, master, "resume", "");
    }

    public static ZkPath createPause(String service, Boolean master) {
        return new ZkPath(service, master, "pause", "");
    }

    public static ZkPath createRun(String service, Boolean master) {
        return new ZkPath(service, master, "run", "");
    }

    public static ZkPath createCancel(String service, Boolean master) {
        return new ZkPath(service, master, "cancel", "");
    }

    public String toPath() {
        if (StringUtils.hasLength(subNode)) {
            return MessageFormat.format(FORMAT_SUBNODE, service, master ? "master" : "self", subNode);
        }
        return MessageFormat.format(FORMAT_SUB, service, master ? "master" : "master");
    }

    public String masterPath() {
        return "/" + service + "/master";
    }

    public String selfPath() {
        return "/" + service + "/self";
    }
}
