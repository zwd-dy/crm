package me.zwdi.crm.settings.service.impl;

import me.zwdi.crm.settings.dao.DicTypeDao;
import me.zwdi.crm.settings.dao.DicValueDao;
import me.zwdi.crm.settings.domain.DicType;
import me.zwdi.crm.settings.domain.DicValue;
import me.zwdi.crm.settings.service.DicService;
import me.zwdi.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();

        // 将字典类型列表取出
        List<DicType> dtList = dicTypeDao.getTypeList();

        // 将字典类型列表遍历
        for (DicType dicType : dtList) {

            // 取得每一种类型的字典类型编码
            String code = dicType.getCode();

            // 根据每一个字典类型来取得字典值列表
            List<DicValue> dvList = dicValueDao.getListByCode(code);
            map.put(code,dvList);
        }

        return map;
    }
}
