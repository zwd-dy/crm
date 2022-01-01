package me.zwdi.crm.workbench.service;

import me.zwdi.crm.vo.PaginationVO;
import me.zwdi.crm.workbench.domain.Activity;
import me.zwdi.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemarkById(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean deleteRemark(String id);

    boolean updateRemark(String id, String noteContent);
}
