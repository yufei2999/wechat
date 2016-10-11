package com.yufei.servlet.com.yufei.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pc on 2016-10-11.
 */
public abstract class SHA1 {

    private static final Logger logger = Logger.getLogger(SHA1.class);

    public static String getDigestOfString(String src) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(src.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            logger.error("sha1 encode error", e);
        }
        return "";
    }

}
