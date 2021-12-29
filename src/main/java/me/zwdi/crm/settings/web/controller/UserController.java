package me.zwdi.crm.settings.web.controller;

import me.zwdi.crm.settings.domain.User;
import me.zwdi.crm.settings.service.UserService;
import me.zwdi.crm.settings.service.impl.UserServiceImpl;
import me.zwdi.crm.utils.MD5Util;
import me.zwdi.crm.utils.PrintJson;
import me.zwdi.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("进入到用户控制器");
        String path = request.getServletPath();

        if("/settings/user/login.do".equals(path)){
            login(request,response);
        } else if("/settings/user/xxx.do".equals(path)) {

            // xxx(request,response)

        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到登录验证操作");

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        // 将密码的明文形式转换为MD5密文
        loginPwd = MD5Util.getMD5(loginPwd);
        // 接收浏览器端IP地址
        String ip = request.getRemoteAddr();
        System.out.println("ip--------"+ip);

        // 未来业务层开发，统一使用代理类形态的接口对象
        UserService us = (UserService)ServiceFactory.getService(new UserServiceImpl());

        try {
            User user = us.login(loginAct,loginPwd,ip);

            request.getSession().setAttribute("user",user);

            // 如果程序执行到此处，说明业务层没有为controller抛出任何的异常
            // 表示登录成功
            /*
                   {"success":true}
             */
            PrintJson.printJsonFlag(response,true);

        } catch (Exception e) {
            e.printStackTrace();

            // 一旦程序执行了catch块的信息，说明业务层为我们验证登录失败，为controller抛出了异常
            // 表示登录失败
            String msg = e.getMessage();
            /*
                    现在作为控制器controller，需要为ajax请求提供多项信息
                    可以有两种手段来处理，
                        1.将多项信息打包成为map，将map解析为json串
                        2.创建一个Vo
                            private boolean success;
                            private String msg;

                         如果对于展现的信息将来还会大量的使用，我们创建一个Vo类，使用方便
                         如果对于展现的信息只有在这个需求中能够使用，我们使用map就可以了
             */
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);

        }




    }
}
