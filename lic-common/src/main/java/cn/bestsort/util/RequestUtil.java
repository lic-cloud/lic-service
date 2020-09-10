package cn.bestsort.util;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 10:56
 */

@Component
public class RequestUtil {

    private static final String[] TRUST_PROXIES_IPS = new String[]{};


    /**
     * Try to get the remote addr even server behind a proxy
     */
    static public String[] getClientIps(HttpServletRequest request) {
        ArrayList<String> forwardedIpsStrs;
        String            xForwardedFor = request.getHeader("x-forwarded-for");

        if (xForwardedFor != null && xForwardedFor.trim().length() > 0) {
            forwardedIpsStrs = new ArrayList<>(
                Arrays.asList(xForwardedFor.trim().split(" *, *")));
        } else {
            forwardedIpsStrs = new ArrayList<>();
        }

        forwardedIpsStrs.add(request.getRemoteAddr());

        return getClientIps(forwardedIpsStrs);

    }

    static public String[] getClientIps(ArrayList<String> forwarded) {
        ArrayList<String> ips = new ArrayList<>();
        int               i   = forwarded.size() - 1;
        for (; i >= 1; i--) {
            if (!IpUtil.isIpInSubnets(forwarded.get(i), TRUST_PROXIES_IPS)) {
                break;
            }
        }
        for (; i >= 0; i--) {
            ips.add(forwarded.get(i));
        }
        return ips.toArray(String[]::new);
    }

    static public String getClientIp(HttpServletRequest request) {
        return getClientIps(request)[0];
    }

}
