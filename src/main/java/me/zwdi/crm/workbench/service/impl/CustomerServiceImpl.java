package me.zwdi.crm.workbench.service.impl;

import me.zwdi.crm.utils.SqlSessionUtil;
import me.zwdi.crm.workbench.dao.CustomerDao;
import me.zwdi.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        return customerDao.getCustomerName(name);
    }
}
