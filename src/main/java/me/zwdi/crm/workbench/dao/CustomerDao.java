package me.zwdi.crm.workbench.dao;

import me.zwdi.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByname(String company);

    int save(Customer cus);

    List<String> getCustomerName(String name);
}
