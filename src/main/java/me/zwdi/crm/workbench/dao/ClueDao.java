package me.zwdi.crm.workbench.dao;


import me.zwdi.crm.workbench.domain.Activity;
import me.zwdi.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue c);

    Clue detail(String id);

    Clue getById(String clueId);

    int delete(String clueId);

    List<Clue> getClueListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);
}
