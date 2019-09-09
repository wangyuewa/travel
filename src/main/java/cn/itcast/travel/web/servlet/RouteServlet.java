package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BeansServlet {



    /*==============分页对象的查询方法======================*/



    protected void findQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        //获取数据即可
        String currentPageS = request.getParameter("currentPage");
        String pageSizeS = request.getParameter("pageSize");
        String cidS = request.getParameter("cid");







        /*========添加模糊查询的方法====添加rname模糊查询的重构======*/






        String rname = request.getParameter("rname");
        //System.out.println(rname);
        /*========进行转码=======*/
        /*========实践证明了此处不需要转码就可以进行数据的传递===============*/
        rname= new String(rname.getBytes("iso-8859-1"), "utf-8");
        //System.out.println("RouteServlet中的rname"+rname);
        int cid=0;
        /*========cidS不仅要不等于空而且cidS的长度还要大于o并且"null"不等于他即可;=========*/

        //客户端如果传递的参数为空时,他会携带过来一个"null"的字符串值,
        if(cidS!=null&&cidS.length()>0&&!"null".equals(cidS)){
            cid = Integer.parseInt(cidS);
        }


        //进行数据的传递
        int currentPage=0;
        if(currentPageS!=null&&currentPageS.length()>0){
            currentPage = Integer.parseInt(currentPageS);
        }else{
            currentPage=1;
        }
        int pageSize=0;
        if(pageSizeS!=null&&pageSizeS.length()>0){
            pageSize = Integer.parseInt(pageSizeS);
        }else{
            pageSize=5;
        }
        //调取服务层的方法进行参数的传递;
        RouteService routeService = new RouteServiceImpl();


        /*=============调取service层的方法=======添加rname变量=======*/


        PageBean<Route> pageBean=routeService.findQuery(cid,currentPage,pageSize,rname);


        //将pagebean序列化出去即可;


        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(pageBean);
        //System.out.println("序列化之后的pagebean对象"+s);
        //调用响应数据的方法response的方法  将数据响应出去即可;


        response.getWriter().write(s);


    }



    /*==================查询详情页面信息的方法,将数据群补都封装到Route这个类里边==========*/




    protected void findDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");

        /*  获取rid信息*/
        String rid = request.getParameter("rid");

        RouteServiceImpl routeService = new RouteServiceImpl();
        Route route=routeService.findDetail(rid);

        ObjectMapper mapper = new ObjectMapper();

        String route_json = mapper.writeValueAsString(route);


        System.out.println("服务器调出的route对象是"+route_json);

        response.getWriter().write(route_json);

    }




    /*=========================定义一个是否收藏的方法==================*/





    protected void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");

        //获取rid的值和uid的值
        String ridStr = request.getParameter("rid");
        //默认是一rid

        System.out.println(ridStr);
        int rid = Integer.parseInt(ridStr);


        User user = (User) request.getSession().getAttribute("user");

        int uid;
        //String msg=null;//定义一个提醒用户还未登陆的实践证明这样写时不行的,得用信息携带类进行数据的传递
        /*ResultInfo resultInfo = new ResultInfo();*/

        if(user==null){
            //user对象为空,未登录;
            uid=0;
           // msg="你还没有登陆,请登录!";
            /*resultInfo.setFlag(true);
            resultInfo.setErrorMsg("你尚未登陆,请登录!");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(),resultInfo);*/
            //直接返回
            return;

        }else{
            //user对象有值,获取uid的值;
            uid=user.getUid();
        }

        FavoriteServiceImpl favoriteService = new FavoriteServiceImpl();

        boolean flag = favoriteService.findFavorite(rid, uid);



        //注意加字符串的""值和不加字符串的值的区别

        //加字符串的返回值在前端判断是不会被解析为false或者true的值,如果不是字符串的子在前端可以进行false或者  ture的判断解析

        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(response.getOutputStream(),flag);


    }




//    /*==============添加一个书藏的方法===============*/




    protected void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");

        //获取rid的值和uid的值
        String ridStr = request.getParameter("rid");
        int rid = Integer.parseInt(ridStr);

        User user = (User) request.getSession().getAttribute("user");

        int uid;
        if("null".equals(user)){
            //user对象为空,未登录;
           return;

        }else{
            //user对象有值,获取uid的值;
            uid=user.getUid();
        }

        FavoriteServiceImpl favoriteService = new FavoriteServiceImpl();

        favoriteService.addFavorite(rid,uid);
    }

}
