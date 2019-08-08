package com.wust.springcloud.autotask.server.config;


import com.wust.springcloud.common.interceptors.MyBatisDefaultInterceptor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import java.sql.Connection;

/**
 * Created by WST on 2019/5/9.
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MyBatisInterceptor extends MyBatisDefaultInterceptor {

}


