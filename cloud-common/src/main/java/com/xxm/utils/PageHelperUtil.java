package com.xxm.utils;


import com.github.pagehelper.PageHelper;
import com.xxm.api.req.QueryReq;


public class PageHelperUtil {

    public static void page(QueryReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
    }

}
