package me.zwdi.crm.workbench.dao;

import me.zwdi.crm.workbench.domain.Activity;
import me.zwdi.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran t);

    List<Tran> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    Tran detail(String id);

    int updateChangeStage(Tran t);
}
