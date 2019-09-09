package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.impl.RegistUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*   ============ 定义一个用户激活邮箱的后续操作 ================*/

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //首先获取客户端?后便携带过来的code  判断其是否为空;
        String code = request.getParameter("code");
        if(code!=null&&code.length()>0){
            RegistUserServiceImpl registUserService = new RegistUserServiceImpl();
            boolean flag=registUserService.active(code);
            String msg=null;
            if(flag){
                //如果繁华之为true;
                msg="<a href='login.html'>激活成功</a>";
            }else{
                //如果返回值位false;
                msg="激活失败,请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request,response);
    }
}
