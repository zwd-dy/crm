package me.zwdi.crm.workbench.service;

import me.zwdi.crm.vo.PaginationVO;
import me.zwdi.crm.workbench.domain.Tran;
import me.zwdi.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    boolean save(Tran t, String customerName);

    PaginationVO<Tran> pageList(Map<String, Object> map);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean updateChangeStage(Tran t);
}
