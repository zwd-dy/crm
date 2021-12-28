package me.zwdi.crm.settings.service.impl;

import me.zwdi.crm.settings.dao.UserDao;
import me.zwdi.crm.settings.service.UserService;
import me.zwdi.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
}
