package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.RegistUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.Location;
import java.io.IOException;

@WebServlet("/loginUserServlet")
public class LoginUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        /*====================验证码验证码=============================*/


        //用户登陆的方法;
        //首先获取验证码;
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //及时销毁即可;
        session.removeAttribute("CHECKCODE_SERVER");
        String check = request.getParameter("check");
        if(checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            //给出提示信息即可;
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            //定义一个输出字符流
            response.setContentType("application/json;charset=utf-8");

            mapper.writeValue(response.getOutputStream(),resultInfo);
            //如果检验错误就直接返回;
            return;
        }




        /*============第二步完成该椒盐即可=============*/



        //获取username和password;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //用username和password 位参数去查询数据库即可;
        RegistUserServiceImpl registUserService = new RegistUserServiceImpl();
        User user =registUserService.findAll(username,password);
        //System.out.println(user);
        //接下爱对其数据进行判断就可以;
        ResultInfo resultInfo = new ResultInfo();
        //如果返回的数据为空,则用户名或者密码错误即可
        if(user==null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误!");
        }
        //如果返回数据不为空但是状态码不是Y  还是不行
        if(user!=null&&!"Y".equals(user.getStatus())){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("你的账户未激活,请激活你的账户");

        }
        if(user!=null&&"Y".equals(user.getStatus())){
            //直接重定向到展示页面
            //response.sendRedirect("/index.html");
            //将登陆成功后的user存入到session域中,方便之后的数据调用;
            resultInfo.setFlag(true);
            request.getSession().setAttribute("user",user);
            String name = user.getName();
            session.setAttribute("name",name);
           /* ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(),name);
*/
        }

        ObjectMapper mapper = new ObjectMapper();
        //设置字符集,写出去的
        response.setContentType("application/json;charset=utf-8");
        //将数据展示出去即可;
        mapper.writeValue(response.getOutputStream(),resultInfo);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
