package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {


    /*=====是否收藏的方法=====*/


    public Favorite findFavotite(int rid, int uid);



    /*=====添加一个显示收藏次数的方法=====*/



    int findCount(int rid);


    /*===========添加收藏的方法===============*/



    void addFavorite(int rid, int uid);




    /*=======客户所有收藏=======*/



    int findTotalCount(int uid);


    /*========查询收藏页面展示的所有列表图片那信息==========*/

    List<Route> findRoute(int uid, int start, int pageSize);
}
