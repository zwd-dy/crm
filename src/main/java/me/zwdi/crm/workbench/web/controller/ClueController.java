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
import me.zwdi.crm.workbench.domain.Clue;
import me.zwdi.crm.workbench.service.ActivityService;
import me.zwdi.crm.workbench.service.ClueService;
import me.zwdi.crm.workbench.service.impl.ActivityServiceImpl;
import me.zwdi.crm.workbench.service.impl.ClueServiceImpl;

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

        if("/workbench/clue/getUserList.do".equals(path)){

            getUserList(request,response);

        } else if("/workbench/clue/save.do".equals(path)) {

            save(request,response);

        } else if("/workbench/clue/detail.do".equals(path)) {

            detail(request,response);

        } else if("/workbench/clue/getActivityListByClueId.do".equals(path)) {

            getActivityListByClueId(request,response);

        }else if("/workbench/clue/unbund.do".equals(path)) {

            unbund(request,response);

        }else if("/workbench/clue/getActivityListByNameAndNotByCluId.do".equals(path)) {

            getActivityListByNameAndNotByCluId(request,response);

        }else if("/workbench/clue/bund.do".equals(path)) {

            bund(request,response);

        }else if("/workbench/clue/getActivityListByName.do".equals(path)) {

            getActivityListByName(request,response);

        }else if("/workbench/clue/convert.do".equals(path)) {

            convert(request,response);

        }

    }

    private void convert(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行线索转换的操作");

        // 接收是否需要创建交易的标记
        String flag = request.getParameter("flag");

        // 如果需要创建交易
        if("a".equals(flag)){
            // 接收交易
        }

    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询市场活动列表（根据名称模糊查）");
        String aname = request.getParameter("aname");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList = as.getActivityListByName(aname);
        PrintJson.printJsonObj(response,aList);
    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行关联市场活动的操作");
        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.bund(cid,aids);
        PrintJson.printJsonFlag(response,flag);

    }

    private void getActivityListByNameAndNotByCluId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询市场活动列表（根据名称模糊查找+排除掉已经关联指定线索的列表）");
        String aname = request.getParameter("aname");
        String clueId = request.getParameter("clueId");

        Map<String,String> map = new HashMap<String, String>();
        map.put("aname",aname);
        map.put("clueId",clueId);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList = as.getActivityListByNameAndNotByCluId(map);
        PrintJson.printJsonObj(response,aList);

    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行解除市场活动关联操作");
        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.unbund(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("跳转到线索详细信息页的获取关联市场活动列表操作");

        String id = request.getParameter("clueId");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList = as.getActivityListByClueId(id);
        PrintJson.printJsonObj(response,aList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到线索详细信息页");

        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue c = cs.detail(id);

        request.setAttribute("c",c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行线索的添加操作");
        Clue c = new Clue();
        WebUtil.makeRequestToObject(request,c);
        c.setId(UUIDUtil.getUUID());

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.save(c);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入线索模块取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response,uList);
    }


}
