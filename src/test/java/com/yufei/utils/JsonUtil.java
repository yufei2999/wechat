package com.yufei.utils;

import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public abstract class JsonUtil {

    private static final Logger logger = Logger.getLogger(JsonUtil.class);
    
    /**
     * json转对象
     * 
     * @param s
     * @param clazz
     * @return
     */
    public static <T> T getObject(String s, Class<T> clazz) {
        try {
            return JSON.parseObject(s, clazz);
        } catch (Exception e) {
            logger.error("---------------- json转对象异常", e);
        }
        return null;
    }
    
    /**
     * json转list
     * 
     * @param s
     * @param clazz
     * @return
     */
    public static <T> List<T> getList(String s, Class<T> clazz) {
        try {
            return JSON.parseArray(s, clazz);
        } catch (Exception e) {
            logger.error("---------------- json转list异常", e);
        }
        return null;
    }

    /**
     * 对象转json串
     * 
     * @param data
     * @return
     */
    public static String toJSONString(Object data) {
        return JSON.toJSONString(data, SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\"");
    }
    
    /**
     * 对象转json串
     * 
     * @param data
     * @return
     */
    public static String toJSONStringIgnoreNullValue(Object data) {
        return JSON.toJSONString(data);
    }
}
