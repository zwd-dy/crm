package me.zwdi.crm.workbench.dao;

import me.zwdi.crm.workbench.domain.Activity;
import me.zwdi.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity a);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity a);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String id);

    List<Activity> getActivityListByNameAndNotByCluId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}
