package com.wust.springcloud.autotask.server.service;

import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerList;
import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerSearch;
import java.util.List;

/**
 * Created by WST on 2019/6/13.
 */
public interface JobService {
    List<QrtzJobAndTriggerList> listPage(QrtzJobAndTriggerSearch search);
}
