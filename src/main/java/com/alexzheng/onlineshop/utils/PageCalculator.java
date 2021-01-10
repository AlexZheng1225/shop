package com.alexzheng.onlineshop.utils;

/**
 * @Author Alex Zheng
 * @Date created in 15:00 2020/4/24
 * @Annotation
 */
public class PageCalculator {

    /**
     *
     * @param pageIndex
     * @param pageSize
     * @return 返回从第几条数据开始取的数字 例如pageIndex为1。pageSize为5，即第一页从0行开始取，取到4
     * 若
     */
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }

}
