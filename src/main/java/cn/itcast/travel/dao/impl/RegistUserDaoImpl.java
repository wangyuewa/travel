package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RegistUserDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RegistUserDaoImpl implements RegistUserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());



    /*===========================第一步  用户注册用户名是否可以使用的方法==================================*/




    @Override
    public User findOne(String username) {

        //定义sql语句
        String sql="select * from tab_user where username=?";
        User user =null;
        try{

            user= template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);


        }catch(Exception e){
            e.printStackTrace();

        }

        return user;
    }

    /* ===================================用户名可以使用直接保存数据即可=====================================*/

    @Override
    public void saveUser(User user) {
        //定义sql语句
        String sql="insert into tab_user value(null,?,?,?,?,?,?,?,?,?)";
        int i = template.update(sql,
                                user.getUsername(), user.getPassword(),
                                user.getName(), user.getBirthday(), user.getSex(),
                                user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode());
        if(i>0){
            System.out.println("新用户添加成功!");
        }else{
            System.out.println("新用户添加失败!");
        }


    }



    /*=================================用户注册功能中的用户的激活码是否激活和=============================================*/






    //邮件激活之后的后续代码的书写
    //第一查询是否里有这样的一个code
    //第二  直接更改状态码wei大写的y;

    @Override
    public User findByCode(String code) {
        //定义查询语句;
        String sql="select * from tab_user where code=?";
        User user=null;
        //一个对象直接可以封装到user里边即可
        try{
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void updateStatus(int uid) {
        //定义sql语句;
        String sql= "update tab_user set status='Y' where uid=?";
        int i = template.update(sql, uid);
        if(i>0){
            System.out.println("用户激活码激活成功!");
        }else{
            System.out.println("用户激活码激活失败!");
        }

    }


    /*=======================登陆查询的方法=======================*/





    @Override
    public User findByAll(String username, String password) {
        //定义sql语句;防止空指针异常;
        User user =null;
        try{
            String sql="select * from tab_user where username=? and password=?";
             user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        }catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println(user);
        return user;

    }




    /*=============================查询数据库的category的分类导航数据的查询方法,返回list集合里边的范型位category的类*/




    @Override
    public List<Category> findAllCategory() {
        //定义查询语句sql
        List<Category> list=null;
        try{
            String sql="select * from tab_category order by cid ";

            //执行查询不需要任何的参数;
             list= template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));

        }catch (Exception e)
        {e.printStackTrace();}

        return list;
    }

}
