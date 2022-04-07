/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.electron.base.common;


import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * 网络、系统工具类
 */
public class RemotingUtil {
    public static final String OS_NAME = System.getProperty("os.name");

    private static boolean isLinuxPlatform = false;
    private static boolean isWindowsPlatform = false;

    static {
        if (OS_NAME != null && OS_NAME.toLowerCase().contains("linux")) {
            isLinuxPlatform = true;
        }

        if (OS_NAME != null && OS_NAME.toLowerCase().contains("windows")) {
            isWindowsPlatform = true;
        }
    }

    public static boolean isWindowsPlatform() {
        return isWindowsPlatform;
    }

    public static Selector openSelector() throws IOException {
        Selector result = null;

        if (isLinuxPlatform()) {
            try {
                final Class<?> providerClazz = Class.forName("sun.nio.ch.EPollSelectorProvider");
                if (providerClazz != null) {
                    try {
                        final Method method = providerClazz.getMethod("provider");
                        if (method != null) {
                            final SelectorProvider selectorProvider = (SelectorProvider) method.invoke(null);
                            if (selectorProvider != null) {
                                result = selectorProvider.openSelector();
                            }
                        }
                    } catch (final Exception e) {
                        //log.warn("Open ePoll Selector for linux platform exception", e);
                    }
                }
            } catch (final Exception e) {
                // ignore
            }
        }

        if (result == null) {
            result = Selector.open();
        }

        return result;
    }

    public static boolean isLinuxPlatform() {
        return isLinuxPlatform;
    }

    public static String getLocalAddress() {
        try {
            // Traversal Network interface to get the first non-loopback and non-private address
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            ArrayList<String> ipv4Result = new ArrayList<String>();
            ArrayList<String> ipv6Result = new ArrayList<String>();
            while (enumeration.hasMoreElements()) {
                final NetworkInterface networkInterface = enumeration.nextElement();
                final Enumeration<InetAddress> en = networkInterface.getInetAddresses();
                while (en.hasMoreElements()) {
                    final InetAddress address = en.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address instanceof Inet6Address) {
                            ipv6Result.add(normalizeHostAddress(address));
                        } else {
                            ipv4Result.add(normalizeHostAddress(address));
                        }
                    }
                }
            }

            // prefer ipv4
            if (!ipv4Result.isEmpty()) {
                for (String ip : ipv4Result) {
                    if (ip.startsWith("127.0") || ip.startsWith("192.168")) {
                        continue;
                    }

                    return ip;
                }

                return ipv4Result.get(ipv4Result.size() - 1);
            } else if (!ipv6Result.isEmpty()) {
                return ipv6Result.get(0);
            }
            //If failed to find,fall back to localhost
            final InetAddress localHost = InetAddress.getLocalHost();
            return normalizeHostAddress(localHost);
        } catch (Exception e) {
            //log.error("Failed to obtain local address", e);
        }

        return null;
    }

    public static String normalizeHostAddress(final InetAddress localHost) {
        if (localHost instanceof Inet6Address) {
            return "[" + localHost.getHostAddress() + "]";
        } else {
            return localHost.getHostAddress();
        }
    }

    public static SocketAddress string2SocketAddress(final String addr) {
        String[] s = addr.split(":");
        InetSocketAddress isa = new InetSocketAddress(s[0], Integer.parseInt(s[1]));
        return isa;
    }

    public static String socketAddress2String(final SocketAddress addr) {
        StringBuilder sb = new StringBuilder();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) addr;
        sb.append(inetSocketAddress.getAddress().getHostAddress());
        sb.append(":");
        sb.append(inetSocketAddress.getPort());
        return sb.toString();
    }

    public static SocketChannel connect(SocketAddress remote) {
        return connect(remote, 1000 * 5);
    }

    public static SocketChannel connect(SocketAddress remote, final int timeoutMillis) {
        SocketChannel sc = null;
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(true);
            sc.socket().setSoLinger(false, -1);
            sc.socket().setTcpNoDelay(true);
            sc.socket().setReceiveBufferSize(1024 * 64);
            sc.socket().setSendBufferSize(1024 * 64);
            sc.socket().connect(remote, timeoutMillis);
            sc.configureBlocking(false);
            return sc;
        } catch (Exception e) {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return null;
    }
}

