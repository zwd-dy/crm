package me.zwdi.crm.settings.dao;

import me.zwdi.crm.settings.domain.User;

import java.util.Map;

public interface UserDao {
    User login(Map<String, String> map);
}
