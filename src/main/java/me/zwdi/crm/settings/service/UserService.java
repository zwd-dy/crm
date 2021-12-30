package me.zwdi.crm.settings.service;

import me.zwdi.crm.exception.LoginException;
import me.zwdi.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
