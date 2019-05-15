package com.cyberiansoft.test.globalutils;

import org.apache.commons.validator.routines.InetAddressValidator;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkUtils {

    private static String testIP;

    public static String getLocalIPAdress() throws SocketException {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                .hasMoreElements(); ) {
            NetworkInterface intf = en.nextElement();
            if ((testIP = getLocalIP(intf)) != null) {
                break;
            }
        }
        return testIP;
    }

    private static String getLocalIP(NetworkInterface intf) {
        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                .hasMoreElements(); ) {
            InetAddress inetAddress = enumIpAddr.nextElement();
            if (!inetAddress.isLoopbackAddress()) {
                InetAddressValidator validator = InetAddressValidator.getInstance();
                String calculated = inetAddress.getHostAddress();
                if (validator.isValid(calculated)) {
                    return calculated;
                }
            }
        }
        return null;
    }
}
