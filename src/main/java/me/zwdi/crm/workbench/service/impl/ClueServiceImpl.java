package me.zwdi.crm.workbench.service.impl;

import me.zwdi.crm.utils.SqlSessionUtil;
import me.zwdi.crm.workbench.dao.ClueDao;
import me.zwdi.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);


}
