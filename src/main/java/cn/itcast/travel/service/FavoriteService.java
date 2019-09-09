package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface FavoriteService {
    public boolean findFavorite(int rid,int uid);



    /*============添加书藏的方法===========*/



    void addFavorite(int rid, int uid);

    PageBean<Route> findQuery(int uid, int currentPage, int pageSize);
}
