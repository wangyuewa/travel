package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;

import java.util.List;

public interface RegistUserDao  {
    User findOne(String username);

    void saveUser(User user);
    /*===============用户激活邮箱操作=======================*/

    User findByCode(String code);

    void updateStatus(int uid);

    User findByAll(String username, String password);



    /*==================定义一个查询数据库分类信息的方法=========================*/



    List<Category> findAllCategory();
}
