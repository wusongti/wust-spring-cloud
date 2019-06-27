package com.wust.springcloud.autotask.server.service.impl;

import com.wust.springcloud.autotask.server.dao.JobMapper;
import com.wust.springcloud.autotask.server.service.JobService;
import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerList;
import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by WST on 2019/6/13.
 */
@Service("jobServiceImpl")
public class JobServiceImpl implements JobService {
    @Autowired
    private JobMapper jobMapper;

    @Override
    public List<QrtzJobAndTriggerList> listPage(QrtzJobAndTriggerSearch search) {
        return jobMapper.listPage(search);
    }
}
