package me.zwdi.crm.workbench.service.impl;

import me.zwdi.crm.settings.dao.UserDao;
import me.zwdi.crm.settings.domain.User;
import me.zwdi.crm.utils.SqlSessionUtil;
import me.zwdi.crm.vo.PaginationVO;
import me.zwdi.crm.workbench.dao.ActivityDao;
import me.zwdi.crm.workbench.dao.ActivityRemarkDao;
import me.zwdi.crm.workbench.domain.Activity;
import me.zwdi.crm.workbench.domain.ActivityRemark;
import me.zwdi.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

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


    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;
        // 查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByids(ids);

        // 删除备注，返回受到影响的条数（实际删除的数量）
        int count2 = activityRemarkDao.deleteByAids(ids);
        if(count1 != count2){

            flag = false;

        }
        // 删除市场活动
       int count3 = activityDao.delete(ids);
        if(count3 != ids.length){

            flag = false;

        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        // 取uList
        List<User> uList = userDao.getUserList();

        // 取a
        Activity a = activityDao.getById(id);

        // 将uList和a打包到map中
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("uList",uList);
        map.put("a",a);
        return map;
    }

    @Override
    public boolean update(Activity a) {
        Boolean flag = true;

        int count = activityDao.update(a);
        if(count != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Activity detail(String id) {
        return activityDao.detail(id);
    }

    @Override
    public List<ActivityRemark> getRemarkById(String id) {
        return activityRemarkDao.getRemarkById(id);
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        Boolean flag = true;

        int count = activityRemarkDao.saveRemark(ar);
        if(count != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        // 删除备注，返回受到影响的条数（实际删除的数量）
        int count = activityRemarkDao.deleteByid(id);
        if(count != 1){

            flag = false;

        }
        return flag;
    }

    @Override
    public boolean updateRemark(String id, String noteContent) {

        boolean flag = true;
        int count = activityRemarkDao.updateRemarkById(id,noteContent);
        if(count != 1){

            flag = false;

        }
        return flag;
    }
}
