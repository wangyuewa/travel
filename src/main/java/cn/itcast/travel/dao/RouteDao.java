package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface RouteDao {


    /*=============定义一个查询总页数的方法==========*/
    int findTotalCount(int cid,String rname);
    /*==============定义一个分页查询的方法=======================*/
    List<Route> findRoute(int cid, int start, int pageSize,String rname);


    /*====查询详情的方法===*/


    Route findDetail(int rid);


    /*=====查询详细里边的图片集合===*/

    List<RouteImg> findDetailImg(int rid);



    /*=====查询商家的方法====*/

    Seller findDetallSeller(int sid);
}
