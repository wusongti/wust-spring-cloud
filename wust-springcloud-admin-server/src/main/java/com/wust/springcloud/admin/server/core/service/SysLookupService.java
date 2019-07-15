package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupSearch;
import java.util.List;

/**
 * Created by WST on 2019/4/29.
 */
public interface SysLookupService {
    List<SysLookupList> listPage(SysLookupSearch search);

    List<SysLookupList> findByCondition(SysLookupSearch search);

    ResponseDto init();
}
