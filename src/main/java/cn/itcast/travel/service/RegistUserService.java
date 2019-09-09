package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;

import java.util.List;

public interface RegistUserService {
    boolean findOne(String username);
    public void saveUser(User user);

    boolean active(String code);

    User findAll(String username, String password);

    List<Category> findAllCategory();
    /*=================对前台数据库分类页面的处理=================*/
    String find();
}
