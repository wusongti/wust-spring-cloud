package com.wust.springcloud.admin.server.service;

import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupSearch;
import java.util.List;

/**
 * Created by WST on 2019/4/29.
 */
public interface SysLookupService {
    List<SysLookupList> listPage(SysLookupSearch search);

    List<SysLookupList> findByCondition(SysLookupSearch search);

    MessageMap init();
}
