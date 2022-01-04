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
import me.zwdi.crm.workbench.domain.Clue;
import me.zwdi.crm.workbench.domain.Tran;
import me.zwdi.crm.workbench.domain.TranHistory;
import me.zwdi.crm.workbench.service.ActivityService;
import me.zwdi.crm.workbench.service.ClueService;
import me.zwdi.crm.workbench.service.CustomerService;
import me.zwdi.crm.workbench.service.TranService;
import me.zwdi.crm.workbench.service.impl.ActivityServiceImpl;
import me.zwdi.crm.workbench.service.impl.ClueServiceImpl;
import me.zwdi.crm.workbench.service.impl.CustomerServiceImpl;
import me.zwdi.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("进入到交易控制器");
        String path = request.getServletPath();

        if("/workbench/transaction/add.do".equals(path)){

            add(request,response);

        } else if("/workbench/transaction/getCustomerName.do".equals(path)) {

            getCustomerName(request,response);

        } else if("/workbench/transaction/save.do".equals(path)) {

            save(request,response);

        } else if("/workbench/transaction/pageList.do".equals(path)) {

            pageList(request,response);

        } else if("/workbench/transaction/detail.do".equals(path)) {

            detail(request,response);

        } else if("/workbench/transaction/getHistoryListByTranId.do".equals(path)) {

            getHistoryListByTranId(request,response);

        } else if("/workbench/transaction/updateChangeStage.do".equals(path)) {

            updateChangeStage(request,response);

        } else if("/workbench/transaction/getCharts.do".equals(path)) {

            getCharts(request,response);
            TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

            /*
                业务层为我们返回
                    total
                    dataList

                    通过map拿到以上两项进行返回
             */
            Map<String,Object> map = ts.getCharts();
            PrintJson.printJsonObj(response,map);
        }

    }

    private void getCharts(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得交易阶段数量统计图表的数据");

    }

    private void updateChangeStage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行改变阶段的操作");

        Tran t = new Tran();
        WebUtil.makeRequestToObject(request,t);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag = ts.updateChangeStage(t);

        Map<String,String> pMap = (Map<String,String>) this.getServletContext().getAttribute("pMap");
        t.setPossibility(pMap.get(t.getStage()));

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("success",flag);
        map.put("t",t);
        PrintJson.printJsonObj(response,map);
    }

    private void getHistoryListByTranId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据交易id取得相应的历史列表");

        String tranId = request.getParameter("tranId");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<TranHistory> thList = ts.getHistoryListByTranId(tranId);

        // 将交易历史遍历
        Map<String,String> pMap = (Map<String,String>) this.getServletContext().getAttribute("pMap");
        for (TranHistory th : thList) {
            // 根据每一条交易历史，取出每一个 阶段
            String stage = th.getStage();
            String possibility = pMap.get(stage);
            th.setPossibilty(possibility);
        }
        PrintJson.printJsonObj(response,thList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到详细信息页");
        String id = request.getParameter("id");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Tran t = ts.detail(id);

        // 处理可能性
        /*
            阶段
            阶段和可能性之间的对应关系 pMap
         */
        String stage = t.getStage();
        ServletContext application = this.getServletContext();
        Map<String,String> pMap = (Map<String,String>) application.getAttribute("pMap");
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);
        request.setAttribute("t",t);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        int pageNo = Integer.valueOf(request.getParameter("pageNo"));
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 计算出sql语句里略过的记录数
        int skipCount = (pageNo - 1) * pageSize;

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        PaginationVO<Tran> vo = ts.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进入到执行添加交易的操作");

        String customerName = request.getParameter("customerName");

        // 封装Tran交易对象
        Tran t = new Tran();
        t.setId(UUIDUtil.getUUID());
        WebUtil.makeRequestToObject(request,t);


        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        boolean flag = ts.save(t,customerName);
        if(flag){
            // 如果添加交易成功，添加到列表页
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }

    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得 客户名称列表（按照客户名称进行模糊查询）");
        String name = request.getParameter("name");

        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<String> sList = cs.getCustomerName(name);
        PrintJson.printJsonObj(response,sList);

    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到跳转到交易添加页的操作");
        UserService us = (UserService)ServiceFactory.getService(new UserServiceImpl());

        List<User> userList = us.getUserList();
        request.setAttribute("uList",userList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }


}
