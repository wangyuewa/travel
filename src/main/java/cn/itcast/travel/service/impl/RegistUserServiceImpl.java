package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RegistUserDao;
import cn.itcast.travel.dao.impl.RegistUserDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RegistUserService;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RegistUserServiceImpl implements RegistUserService {
    private RegistUserDao registUserDao = new RegistUserDaoImpl();
    @Override
    public boolean findOne(String username) {

        User user=registUserDao.findOne(username);
        if(user!=null){
            //如果不为空,则返回false
            return false;

        }else{
            //如果为空  则返回true
            return true;
            //registUserDao.saveUser(user);
        }

    }

    @Override
    public void saveUser(User user) {

        RegistUserDao registUserDao = new RegistUserDaoImpl();
        registUserDao.saveUser(user);
    }




    /*==========用户激活邮箱操作======================*/





    //创建一个激活验证码和保存客户状态吗的方法


    /*==1.1用户验证码激活和注册功能的分析---------首先用户提交的数据里边状态和状态码,初始化只是将用户的状态吗设置为N,在服务器层将客户的状态吗
     *进行体检设置,
     * 第二步就是给客户发送邮件(邮件里边包含  超链接里边包含一个地址拼接一个服务器?code user.getcode 的激活码拼接在服务器里边,
     * 第三部 当客户点击超链接之后,进行数据的的后台处理,获取超链接里边传递错来的code 作为参数在数据库里边进行查询相对应的user对象,进行判断
     * user对象是否为空,
     *
     *
     *
     *
     *
     *
     *
     * ) */

    @Override
    public boolean active(String code) {

        User user=registUserDao.findByCode(code);//返回一个user对象
        //判断对象是否为空;
        if(user!=null){
            //有这样的对象,就给他设置状态码位Y
            registUserDao.updateStatus(user.getUid());
            return true;
        }else {

            return false;
        }

    }


    /*======================定义一个用username和password的查询方法,去查询数据库中所有的数据用于用户登陆使用*/



    @Override
    public User findAll(String username, String password) {
        //调动层的数据即可;
        User user=registUserDao.findByAll(username,password);
        return user;
    }




    /*=======================定义从数据库查询分页展示的数据的查询方法======================*/




    @Override
    public List<Category> findAllCategory() {
        //此业务在service层没有具体的逻辑处理  只是在数据库层调取数据即可;
        List<Category> list=registUserDao.findAllCategory();
        return list;
    }
    /*=============================对findAllCategory的优化处理*/

    @Override
    public String find() {
        //获取redis数据哭连接池对象的黎阿姐资源;
        System.out.println("没有数据在数据库中调取后赋值");
        Jedis jedis = JedisUtil.getJedis();
        String category_json = jedis.get("category");
        //判断是否有值;
        if(category_json==null||category_json.length()==0){
            //确信其为空就进行对其赋值操作;
            List<Category> list = registUserDao.findAllCategory();
            //将list序列化位集合;
            ObjectMapper mapper = new ObjectMapper();
            //序列胡之后的Category对象;
            try {
                category_json= mapper.writeValueAsString(list);

                jedis.set("category",category_json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("缓存中有值,直接使用即可");
            return category_json;
        }


        return category_json;
    }

}
