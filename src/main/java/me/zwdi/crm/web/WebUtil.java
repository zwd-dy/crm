package me.zwdi.crm.web;

import me.zwdi.crm.settings.domain.User;
import me.zwdi.crm.utils.DateTimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

public class WebUtil {

    /**
     * 要想使用以下工具方法有前提：
     *          -- javabean的属性类型需要是String类型
     *          -- 必须保证javabean的属性名和网页表单中的input标签的name属性值相同
     * 将request对象中存储的表单数据设置到javabean对象中
     * @param request 含有表单数据的请求对象
     * @param obj   javabean对象
     */
    public static void makeRequestToObject(HttpServletRequest request,Object obj){
        String path = request.getServletPath();

        String timeType = path.contains("update") ? "editTime" : "createTime";
        String byType = path.contains("update") ? "editBy" : "createBy";
        // 整个Object类的字节码
        Class classO = obj.getClass();

        // 获取表单中所有input标签name属性的值(等同于已经获取了javabean中所有的属性名)
        Enumeration<String> fieldNames = request.getParameterNames();

        while(fieldNames.hasMoreElements()){
            // 获取属性名
            String fieldName = fieldNames.nextElement();
            // 获取方法名
            String methodname = "set"+FirstCharToCase(fieldName);

            // 获取要调用的方法
            try {
                Method setMethod = classO.getDeclaredMethod(methodname, String.class);
                // 调用set方法
                setMethod.invoke(obj,request.getParameter(fieldName));
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        Field[] declaredFields = classO.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            String methodname = "set"+FirstCharToCase(fieldName);
            try {

                if(timeType.equals(fieldName)) {
                    classO.getDeclaredMethod(methodname, String.class).invoke(obj, DateTimeUtil.getSysTime());

                } else if(byType.equals(fieldName)) {
                    classO.getDeclaredMethod(methodname, String.class).invoke(obj, ((User)request.getSession().getAttribute("user")).getName());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }



    private static String FirstCharToCase(String string){
        return string.toUpperCase().charAt(0)+string.substring(1);
    }
}
