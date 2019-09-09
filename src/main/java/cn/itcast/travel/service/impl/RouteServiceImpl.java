package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {



    /*================分页对象的查询=================================*/
            /*======添加rname这一变量========*/



    @Override
    public PageBean<Route> findQuery(int cid, int currentPage, int pageSize,String rname) {
        //创建对象封装数据即可;
        RouteDao routeDao = new RouteDaoImpl();
        PageBean<Route> pb = new PageBean<>();
        //添加当前页码
        pb.setCurrentPage(currentPage);
        //添加煤业显示的条数;
        pb.setPageSize(pageSize);

        //添加总的记录数;totalCount;需要的数据库中查

        /*======变更变量需要变更两个dao层的方法======routeDao*/
        int totalCount=routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //添加总页数
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        int start=(currentPage-1)*pageSize;

        //添加route集合;
        List<Route> route=routeDao.findRoute(cid,start,pageSize,rname);
        pb.setList(route);

        return pb;
    }




    /*===========第四大步   创建一个查询详细页面列表的方法将其封装到Route这一个类中=============*/




    @Override
    public Route findDetail(String rid) {


        /*======需要在route类中封装三种不同的数据,类中装类即可======*/

        /*  第一步调取详细录像的记录===*/

        RouteDaoImpl routeDao = new RouteDaoImpl();



        Route route= routeDao.findDetail(Integer.parseInt(rid));


        /*=======调取图片的集合的方法,传递的还是rid进行查找*/
        /*第二步*/


       List<RouteImg> routeImg=routeDao.findDetailImg(Integer.parseInt(rid));
       route.setRouteImgList(routeImg);


       /*第三部*/

       Seller seller= routeDao.findDetallSeller(route.getSid());
       route.setSeller(seller);


       /*第四步*/  //将需要的额收藏数据夜间如到里边;

        FavoriteDao favoriteDao = new FavoriteDaoImpl();
        int count =favoriteDao.findCount(Integer.parseInt(rid));
        route.setCount(count);

        return route;
    }
}
