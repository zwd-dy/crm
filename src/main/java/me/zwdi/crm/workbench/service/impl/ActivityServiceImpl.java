package me.zwdi.crm.workbench.service.impl;

import me.zwdi.crm.utils.SqlSessionUtil;
import me.zwdi.crm.vo.PaginationVO;
import me.zwdi.crm.workbench.dao.ActivityDao;
import me.zwdi.crm.workbench.domain.Activity;
import me.zwdi.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public boolean save(Activity a) {
        Boolean flag = true;

        int count = activityDao.save(a);
        if(count != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        // 取得total
        int total = activityDao.getTotalByCondition(map);

        // 取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        // 将total和dataList封装到vo中
        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }
}
