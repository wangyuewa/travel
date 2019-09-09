package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {


    /*=============数据库查询总页数的方法==================*/
    /*=======方法重构加上rname模糊查询这一变量=========*/


    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid,String rname) {


        /*=============原有的方法=============*/


        //定义sql语句;
       /* int totalCount=0;
        try{
            String sql="select count(*) from tab_route where cid=?";
            totalCount = template.queryForObject(sql, Integer.class, cid);
        }catch (Exception e){
            e.printStackTrace();
        }*/


       /*===================方法的重构=================*/


        //定义一个sql模板对其进行添加操作
        System.out.println("RouteDao里边的rname"+rname);

        String sql="select count(*) from tab_route where 1=1";
        StringBuffer stringBuffer = new StringBuffer(sql);
        List params = new ArrayList<>();
        if (cid>0){
            stringBuffer.append(" and cid = ? ");
            params.add(cid);

        }
        if(rname!=null&&rname.length()>0&&!"null".equals(rname)){
            /*=====rname有值======*/
            stringBuffer.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        /*===数据的回复===*/
        sql = stringBuffer.toString();
        Integer totalCount = template.queryForObject(sql, Integer.class, params.toArray());

        //自动拆箱;
        return totalCount;
    }


    /*================定义一个分页查询的方法将数据封装到route集合数据里边==================*/

    @Override
    public List<Route> findRoute(int cid, int start, int pageSize,String rname) {


        /*==============原有的方法=================*/
        //定义sql语句即可;
      /*  List<Route> route=null;
        try{
            String sql="select * from tab_route where cid=? limit ?,?";
             route = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid, start, pageSize);
        }catch (Exception e){
            e.printStackTrace();
        }*/

      /*=================方法的重构==================*/
        //定义sql模板
        String sql="select * from tab_route where 1=1 ";
        StringBuffer stringBuffer = new StringBuffer(sql);
        List params = new ArrayList<>();
        //如果cid有值,servlet层已经处理过一些数据,如果没有数值则不需要传递这一变量,也能查询出结果
        if(cid>0){
            stringBuffer.append(" and cid = ? ");
            params.add(cid);
        }
        //对于"null"字符串的解释,,当前端页面床底过来的参数为空时,他会自动转换为一个叫'null'的字符串的值,在此处也应该排除掉
        if(rname!=null&&rname.length()>0&&!"null".equals(rname)){
            stringBuffer.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        stringBuffer.append(" limit ?,? ");
        params.add(start);
        params.add(pageSize);
        sql = stringBuffer.toString();
        System.out.println(sql);
        System.out.println(params);

        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());

        return list;
    }




    /*============第四大步  查询详情页面的方法并且将其封装到一个route类中===========*/




/*1.1*/
    @Override
    public Route findDetail(int rid) {

        Route route=null;

        try {
            String sql="select * from tab_route where rid=? ";
            route= template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        }catch (Exception e){
            e.printStackTrace();
        }

        return route;
    }
    /*1.2*/

    @Override
    public List<RouteImg> findDetailImg(int rid) {
        List<RouteImg> list_routeimg=null;
        try{
            String sql="select * from tab_route_img where rid = ?";
             list_routeimg = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        }catch (Exception e){
            e.printStackTrace();
        }


        return list_routeimg;
    }


    /*1.3*/
    @Override
    public Seller findDetallSeller(int sid) {
        Seller seller=null;
        try{
            String sql="select * from tab_seller where sid=?";
            seller = template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);

        }catch (Exception e){
            e.printStackTrace();
        }

        return seller;
    }
}
