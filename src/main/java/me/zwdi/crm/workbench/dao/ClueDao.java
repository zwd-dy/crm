package me.zwdi.crm.workbench.dao;


import me.zwdi.crm.workbench.domain.Clue;

public interface ClueDao {


    int save(Clue c);

    Clue detail(String id);
}
