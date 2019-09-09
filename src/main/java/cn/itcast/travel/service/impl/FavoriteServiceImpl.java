package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    @Override
    public boolean findFavorite(int rid, int uid) {
        FavoriteDaoImpl favoriteDao = new FavoriteDaoImpl();
        Favorite favotite = favoriteDao.findFavotite(rid, uid);
       return favotite!=null;

    }



    /*============添加书藏的方法===========*/



    @Override
    public void addFavorite(int rid, int uid) {
        FavoriteDao favoriteDao = new FavoriteDaoImpl();
        favoriteDao.addFavorite(rid,uid);

    }






    /*==========收藏页面的页码和route数据的展示功能============*/




    @Override
    public PageBean<Route> findQuery(int uid, int currentPage, int pageSize) {

        FavoriteDao favoriteDao = new FavoriteDaoImpl();

        PageBean<Route> pb = new PageBean<>();
        //添加当前页码
        pb.setCurrentPage(currentPage);
        //添加页显示页码的大小
        pb.setPageSize(pageSize);
        //添加总的页码
        int totalCount=favoriteDao.findTotalCount(uid);
        pb.setTotalCount(totalCount);
        //计算总页数
        int totalPage = totalCount%pageSize==0 ? totalCount/pageSize:totalCount/pageSize+1;

        pb.setTotalPage(totalPage);
        //计算开始页码
        int start=(currentPage-1)*pageSize;

        //添加总的路线展示集合;

        List<Route> routes=favoriteDao.findRoute(uid,start,pageSize);

        pb.setList(routes);



        return pb;
    }
}
