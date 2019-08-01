package com.sigma.sigmacore.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/10/21-18:50
 * desc:
 **/
public class MachineUtil {

    public static String getServerIp() {

        List<String> ips = new ArrayList<>();
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        InetAddress ip = null;

        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
            if (!ni.getName().contains(":")) {
                Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    ip = inetAddresses.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        ips.add(ip.getHostAddress());
                        break;
                    }
                }
            }
        }

        return String.join(",", ips);
    }

    public static String getMachineName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
}
