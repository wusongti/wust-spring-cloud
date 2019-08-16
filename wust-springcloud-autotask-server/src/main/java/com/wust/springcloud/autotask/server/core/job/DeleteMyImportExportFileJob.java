package com.wust.springcloud.autotask.server.core.job;


import com.wust.springcloud.autotask.server.core.service.SysImportExportService;
import com.wust.springcloud.common.annotations.JobAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by WST on 2019/6/13.
 */

@Component
@JobAnnotation(jobName="DeleteMyImportExportFileJob",jobDescription="定期删除导入导出文件",jobGroupName="system",cronExpression="0 0 0 */1 * ?")
public class DeleteMyImportExportFileJob implements BaseJob{
    @Autowired
    private SysImportExportService sysImportExportServiceImpl;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DefaultBusinessContext.getContext().setDataSourceId("wust");

        //sysImportExportServiceImpl.selectByExample(null)

    }
}
