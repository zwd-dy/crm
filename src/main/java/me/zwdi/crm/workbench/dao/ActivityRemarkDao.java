package me.zwdi.crm.workbench.dao;

import me.zwdi.crm.workbench.domain.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkById(String id);

    int saveRemark(ActivityRemark ar);

    int deleteByid(String id);

    int updateRemarkById(@Param("id")String id, @Param("noteContent")String noteContent);
}
