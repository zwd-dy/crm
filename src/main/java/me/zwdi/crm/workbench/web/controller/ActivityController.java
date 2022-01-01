package me.zwdi.crm.workbench.web.controller;

import me.zwdi.crm.settings.domain.User;
import me.zwdi.crm.settings.service.UserService;
import me.zwdi.crm.settings.service.impl.UserServiceImpl;
import me.zwdi.crm.utils.*;
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

public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("进入到用户控制器");
        String path = request.getServletPath();

        if("/workbench/activity/getUserList.do".equals(path)){

            getUserList(request,response);

        } else if("/workbench/activity/save.do".equals(path)) {

            save(request,response);

        }else if("/workbench/activity/pageList.do".equals(path)) {

            pageList(request,response);

        }else if("/workbench/activity/delete.do".equals(path)) {

            delete(request,response);

        }else if("/workbench/activity/getUserListAndActivity.do".equals(path)) {

            getUserListAndActivity(request,response);

        }else if("/workbench/activity/update.do".equals(path)) {

            update(request,response);

        }else if("/workbench/activity/detail.do".equals(path)) {

            detail(request,response);

        }else if("/workbench/activity/getRemarkById.do".equals(path)) {

            getRemarkById(request,response);

        }else if("/workbench/activity/saveRemark.do".equals(path)) {

            saveRemark(request,response);

        }else if("/workbench/activity/deleteRemark.do".equals(path)) {

            deleteRemark(request,response);

        }else if("/workbench/activity/updateRemark.do".equals(path)) {

            updateRemark(request,response);

        }

    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到修改备注信息的操作");
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.updateRemark(id,noteContent);
        PrintJson.printJsonFlag(response,flag);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到删除备注的操作");

        String activityId = request.getParameter("activityId");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);

    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到添加备注操作");

        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        ActivityRemark ar = new ActivityRemark();
        ar.setId(UUIDUtil.getUUID());
        WebUtil.makeRequestToObject(request,ar);
        boolean flag = as.saveRemark(ar);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getRemarkById(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询所有备注操作");

        String id = request.getParameter("id");
        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> activityRemarks = as.getRemarkById(id);
        PrintJson.printJsonObj(response,activityRemarks);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到跳转到详细信息页的操作");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = as.detail(id);
        request.setAttribute("a",a);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动修改操作");

        Activity a = new Activity();
        WebUtil.makeRequestToObject(request,a);

        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.update(a);

        PrintJson.printJsonFlag(response,flag);

    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询用户信息列表和根据市场活动id查询单条记录的操作");

        String id = request.getParameter("id");
        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        /*

                总结：
                    controller调用service方法，返回值应该是什么
                    你得想一想前端要什么，就要从service层取什么

                uList
                a
         */
        Map<String,Object> map = as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);
    }


    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动删除操作");

        String ids[] = request.getParameterValues("id");
        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.delete(ids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int pageNo = Integer.valueOf(request.getParameter("pageNo"));
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 计算出sql语句里略过的记录数
        int skipCount = (pageNo - 1) * pageSize;

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());

        /*
            前端要：市场活动信息列表
                   查询的总条数

                   业务层拿到以上两层信息之后，如何做返回
                   map
                   vo

                   PaginationVO<T>
                    private int total;
                    private List<T> dataList;

                   PaginationVO<Activity> vo = new PaginationVO<>();
                   vo.setTotal(total);
                   vo.setDataList(dataList);
                   PrintJSON vo --> json

                  将来分页查询，每个模块都有，所以我们选择使用一个通用的vo，操作起来比较方便
         */
        PaginationVO<Activity> vo = as.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的添加操作");

        // 封装Activity对象
        Activity a = new Activity();
        a.setId(UUIDUtil.getUUID());
       /* String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");*/
        WebUtil.makeRequestToObject(request,a);

        // 创建时间
        /*String createTime = DateTimeUtil.getSysTime();
        a.setCreateTime(createTime);
        // 创建人，当前登录用户
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        a.setCreateBy(createBy);*/


        /*String editTime = request.getParameter("editTime");
        String editBy = request.getParameter("editBy");*/


        ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(a);

        PrintJson.printJsonFlag(response,flag);
    }


    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService us = (UserService)ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(response,uList);
    }


}
