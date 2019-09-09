package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    PageBean<Route> findQuery(int cid, int currentPage, int pageSize,String rname);



    /*===========第四大步   创建一个查询详细页面列表的方法将其封装到Route这一个类中=============*/



    Route findDetail(String rid);
}
