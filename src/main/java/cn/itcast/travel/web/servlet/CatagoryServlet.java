package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.RegistUserService;
import cn.itcast.travel.service.impl.RegistUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CatagoryServlet extends BeansServlet {



    /*============定义一个页面分类数据展示的功能,查取数据库中的tab_category中的数据  findAll方法=====================*/



    protected void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("applicatin/json;charset=utf-8");
        //调取service层的数据
        RegistUserService service = new RegistUserServiceImpl();

        /*List<Category> list=service.findAllCategory();
        //将集合序列化到页面,让回滚函数调取使用;
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(list);

        System.out.println("category系列化之后的数据格式"+s);*/
        String s = service.find();

        //System.out.println("category系列化之后的数据格式"+s);

        response.getWriter().write(s);


    }
}
