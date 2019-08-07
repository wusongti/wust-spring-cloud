package com.wust.springcloud.admin.server.interceptors;


import com.wust.springcloud.common.adapter.MyBatisInterceptorAdapter;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import java.sql.Connection;

/**
 * Created by WST on 2019/5/9.
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MyBatisInterceptor extends MyBatisInterceptorAdapter {

}


