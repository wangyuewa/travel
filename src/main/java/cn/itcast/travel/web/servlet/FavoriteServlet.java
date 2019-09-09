package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BeansServlet {




    /*================收藏数据的前段展示=================*/




    protected void findQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        String currentPageStr = request.getParameter("currentPage");
        //1.1 获取当前页码
        int currentPage=0;
        if(currentPageStr!=null&&currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);

        }else{
            currentPage=1;
        }

        //1.2 获取每一页显示的条数

        String pageSizeStr = request.getParameter("pageSize");
        int pageSize=0;
        if(pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(currentPageStr);

        }else{
            //设置默认值为5
            pageSize=10;
        }

        //1.3 获取用户的uid  按照uid 进行客户的查询;

        User user = (User) request.getSession().getAttribute("user");
        int uid=0;
        if(user!=null){
           uid = user.getUid();

        }else{
            return;
        }


        //调取service  层的方法

        FavoriteService favoriteService = new FavoriteServiceImpl();
        PageBean<Route> pageBean=favoriteService.findQuery(uid,currentPage,pageSize);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(pageBean);
        System.out.println("收藏路线的收藏展示页面的服务器数据"+s);
        response.getWriter().write(s);


    }

}
