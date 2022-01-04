package me.zwdi.crm.workbench.service;

import me.zwdi.crm.vo.PaginationVO;
import me.zwdi.crm.workbench.domain.Clue;
import me.zwdi.crm.workbench.domain.Tran;

import java.util.Map;

public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId,Tran t,String createBy);

    PaginationVO<Clue> pageList(Map<String, Object> map);
}
