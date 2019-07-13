package com.wust.springcloud.autotask.server.core.dao;

import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerList;
import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerSearch;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by WST on 2019/6/13.
 */
public interface JobMapper {
    List<QrtzJobAndTriggerList> listPage(QrtzJobAndTriggerSearch search) throws DataAccessException;
}
