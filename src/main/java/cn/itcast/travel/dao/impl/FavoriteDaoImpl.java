package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Rid;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {


    /*=====是否收藏的方法=====*/
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Favorite findFavotite(int rid, int uid) {
        Favorite favorite=null;
        try{
            String sql="select * from tab_favorite where rid=? and uid = ?";
            favorite= jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }catch(Exception e){
            e.printStackTrace();
        }

        return favorite;
    }



    /*=====添加一个显示收藏次数的方法=====*/




    @Override
    public int findCount(int rid) {
        Integer integer=null;
        try{
            String sql="select count(*) from tab_favorite where rid=?";
            integer = jdbcTemplate.queryForObject(sql, Integer.class, rid);

        }catch (Exception e){
            e.printStackTrace();
        }

        return integer;
    }



    /*===================添加一个收藏的方法=================*/




    @Override
    public void addFavorite(int rid, int uid) {
        String sql="insert into tab_favorite values (?,?,?)";
        int update = jdbcTemplate.update(sql, rid, new Date(), uid);
        if(update>0){
            System.out.println("收藏添加成功");
        }else{
            System.out.println("添加失败");
        }
    }





    /*================用户的所有的收藏总数===============*/



    @Override
    public int findTotalCount(int uid) {
        Integer integer=null;
         try {
             String sql="select count(*) from tab_favorite where uid = ?";
             integer = jdbcTemplate.queryForObject(sql, Integer.class, uid);

                 }catch (Exception e){
                     e.printStackTrace();
                 }


        return integer;
    }




    /*============用uid查询rid 在用rid的值去查询route对象  将其封装到一个list集合中,泛型为Route类=============*/

    @Override
    public List<Route> findRoute(int uid, int start, int pageSize) {


        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql="select * from tab_favorite where uid=?";
        //List<Integer> rids = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Integer>(Integer.class), 4);



        /*============查询所有的rid对象,将其全部封装到List集合Rid类中===============*/




        List<Rid> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Rid>(Rid.class), uid);

        Route route = null;

        List<Route> list = new ArrayList<>();


        /*========如果产出来的rid的值比我们需要的数据要多,进行限制操作,时使其否和我们的需求===============*/


        if(query.size()>start+pageSize){
            for (int i = start; i < start+pageSize; i++) {

                Rid rid = query.get(i);//获取每一个集合元素,

                Integer rid1 = rid.getRid();
                String sqls="select * from tab_route where rid=? ";

                route = jdbcTemplate.queryForObject(sqls, new BeanPropertyRowMapper<Route>(Route.class), rid1);

                list.add(route);


            }
        }else{
            for (int i = start; i < query.size(); i++) {

                Rid rid = query.get(i);

                Integer rid1 = rid.getRid();
                String sqls="select * from tab_route where rid=? ";

                route = jdbcTemplate.queryForObject(sqls, new BeanPropertyRowMapper<Route>(Route.class), rid1);

                list.add(route);


            }

        }


        return list;

        //获取所有的rid,更具uid的数字产寻rid的值
       /* String sql="select rid from tab_favorite where uid=?";
        List<Integer> rids = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Integer>(Integer.class), uid);

        Route route = null;


        List<Route> list = new ArrayList<>();
        for (int i = 0; i <rids.size() ; i++) {
            Integer rid = rids.get(i);

            String sqls="select * from tab_route where rid=?";
            route = jdbcTemplate.queryForObject(sqls, new BeanPropertyRowMapper<Route>(Route.class), rid);

            list.add(route);*/


    }
}
