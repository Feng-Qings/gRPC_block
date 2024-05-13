package com.block.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommonUtil {

    /**
     * 获取本地ip
     */
    public static String getLocalIp() {
		try {
            InetAddress ip4 = InetAddress.getLocalHost();
            return ip4.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
        }
        return "";
    }
}