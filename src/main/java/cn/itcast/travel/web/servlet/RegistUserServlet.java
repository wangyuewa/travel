package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.RegistUserServiceImpl;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //验证码的验证
        //1.2 获取软件自动生成的验证码;
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //获取用户输入的验证码

        //创建实体类携带信息;

        if(!check.equalsIgnoreCase(checkcode_server)){
            //作用域仅限于此类
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误!");
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json:charset=utf-8");

            mapper.writeValue(response.getOutputStream(),resultInfo);


            //如果两个验证码不相等,则直接原路返回
            return;
        }


        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        //封装数据
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service层的数字,进行查找和保存
        RegistUserServiceImpl registUserService = new RegistUserServiceImpl();

        boolean flag=registUserService.findOne(user.getUsername());
        //创建信息携带传到累
        //false user不为空
        //true user为空;
       /* ResultInfo resultInfo = new ResultInfo();*/
        ResultInfo resultInfo = new ResultInfo();
        if(flag){
            //若果返回为空,则用户名存在;
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("用户名可用!");
            //用户明可用时保存用户;


            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");

            registUserService.saveUser(user);
            //发送邮件
            String content="<a href='http://localhost/travel/activeUserServlet?code="+user.getCode()+"'>点击激活=黑马旅游网=</a>";
            MailUtils.sendMail(user.getEmail(),content,"激活邮件");




        }else{
            //如果返回值不为空  则可以注册,调动方法进行保存操作;

            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名太受欢迎,请再换一个!");
        }

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
