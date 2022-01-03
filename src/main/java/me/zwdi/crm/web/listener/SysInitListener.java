package me.zwdi.crm.web.listener;

import me.zwdi.crm.settings.domain.DicValue;
import me.zwdi.crm.settings.service.DicService;
import me.zwdi.crm.settings.service.impl.DicServiceImpl;
import me.zwdi.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {


    /*
            该方法是用来监听上下文域对象的方法，当副武器启动，上下文域对象创建
            对象创建完毕后，马上执行该方法

            event：该参数能够取得监听的对象
                    监听的是什么对象，就可以通过该参数取得什么对象
                    例如我们现在监听的是上下文域对象，通过该参数就可以取得上下文域对象
     */

    @Override
    public void contextInitialized(ServletContextEvent event) {

        System.out.println("服务器缓存处理数据字典开始");

        ServletContext application = event.getServletContext();

        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());

        /*
                应该管业务层要
                7个List
         */
        // 取数据字典
        Map<String, List<DicValue>> map = ds.getAll();
        // 将map解析为上下文域对象中保存的键值对
        Set<String> set = map.keySet();
        application.setAttribute("dicNum",map.size());

        for (String key : set) {
            application.setAttribute(key,map.get(key));
        }
        // application.setAttribute("","");
        System.out.println("服务器缓存处理数据字典结束");

        // -------------------------------------------------------------------------------------
        // 处理完数据字典后，处理stage2Possibility.properties文件
        /*
            解析该文件，将属性文件中的键值对关系处理成为java中键值对关系（map）

            Map<String stage(阶段),String possibility(可能性)>
         */

        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Map<String,String> pMap = new HashMap<String, String>();

        Enumeration<String> keys = rb.getKeys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            pMap.put(key,rb.getString(key));
        }
        // 将 pMap 保存到服务器缓存中
        application.setAttribute("pMap",pMap);
    }

}
