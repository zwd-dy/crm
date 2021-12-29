import me.zwdi.crm.settings.dao.UserDao;
import me.zwdi.crm.settings.domain.User;
import me.zwdi.crm.utils.MD5Util;
import me.zwdi.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void testMd5(){
        System.out.println(MD5Util.getMD5("123"));
    }

    @org.junit.Test
    public void testMybatis(){
        UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        Map<String,String> map = new HashMap<String,String>();
        map.put("loginAct","zs");
        map.put("loginPwd","202cb962ac59075b964b07152d234b70");
        User user = userDao.login(map);
        System.out.println(user);

    }
}
