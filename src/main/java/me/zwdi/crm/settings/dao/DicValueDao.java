package me.zwdi.crm.settings.dao;

import me.zwdi.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
