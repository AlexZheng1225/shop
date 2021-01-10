package com.alexzheng.onlineshop.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Alex Zheng
 * @Date created in 14:14 2020/4/7
 * @Annotation
 */
@Configuration
public class PathUtil {

    //    获取系统分隔符
    private static String seperator = "/";

    private static String winPath;

    private static String linuxPath;

    private static String shopPath;

    @Value("${win.base.path}")
    public void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }

    @Value("${linux.base.path}")
    public void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }

    @Value("${shop.relevant.path}")
    public void setShopPath(String shopPath) {
        PathUtil.shopPath = shopPath;
    }

    //    返回项目根路径
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = winPath;
        } else {
            basePath = linuxPath;
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

//    返回项目图片子路径
    public static String getShopImagePath(long shopId) {
        String imagePath = shopPath + shopId + "/";
        return imagePath.replace("/", seperator);
    }

}
