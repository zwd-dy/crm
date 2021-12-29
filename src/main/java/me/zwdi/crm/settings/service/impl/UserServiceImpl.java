package me.zwdi.crm.settings.service.impl;

import me.zwdi.crm.exception.LoginException;
import me.zwdi.crm.settings.dao.UserDao;
import me.zwdi.crm.settings.domain.User;
import me.zwdi.crm.settings.service.UserService;
import me.zwdi.crm.utils.DateTimeUtil;
import me.zwdi.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,String> map = new HashMap<String, String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        if(user == null){
            throw new LoginException("账号密码错误");
        }

        // 如果程序能够成功的执行到该行，说明密码正确
        // 需要继续向下验证其他3项信息

        // 验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime) < 0){
            throw new LoginException("账号已失效");
        }

        // 判断锁定状态
        String lockState = user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("账号已锁定");
        }

        // 判断IP地址
        String allowIps = user.getAllowIps();

        if(!allowIps.contains(ip)){
            throw new LoginException("ip地址受限");
        }

        return user;
    }
}
