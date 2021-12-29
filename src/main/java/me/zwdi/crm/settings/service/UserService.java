package me.zwdi.crm.settings.service;

import me.zwdi.crm.exception.LoginException;
import me.zwdi.crm.settings.domain.User;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}
