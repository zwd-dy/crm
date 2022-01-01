package me.zwdi.crm.workbench.web.controller;

import me.zwdi.crm.settings.domain.User;
import me.zwdi.crm.settings.service.UserService;
import me.zwdi.crm.settings.service.impl.UserServiceImpl;
import me.zwdi.crm.utils.PrintJson;
import me.zwdi.crm.utils.ServiceFactory;
import me.zwdi.crm.utils.UUIDUtil;
import me.zwdi.crm.vo.PaginationVO;
import me.zwdi.crm.web.WebUtil;
import me.zwdi.crm.workbench.domain.Activity;
import me.zwdi.crm.workbench.domain.ActivityRemark;
import me.zwdi.crm.workbench.service.ActivityService;
import me.zwdi.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("进入到线索控制器");
        String path = request.getServletPath();

        if("/workbench/clue/xxx.do".equals(path)){

            //getUserList(request,response);

        } else if("/workbench/clue/xxx.do".equals(path)) {

            //save(request,response);

        }

    }


}
