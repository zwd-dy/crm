package me.zwdi.crm.workbench.dao;


import me.zwdi.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {


    int unbund(String id);

    int bund(ClueActivityRelation car);
}
