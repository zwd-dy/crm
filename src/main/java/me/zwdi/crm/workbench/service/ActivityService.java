package me.zwdi.crm.workbench.service;

import me.zwdi.crm.vo.PaginationVO;
import me.zwdi.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);
}
