package com.wust.springcloud.admin.server.core.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysCompanyMapper;
import com.wust.springcloud.admin.server.core.dao.SysDataSourceMapper;
import com.wust.springcloud.admin.server.core.dao.SysUserMapper;
import com.wust.springcloud.admin.server.core.service.SysDataSourceService;
import com.wust.springcloud.admin.server.core.service.SysLookupService;
import com.wust.springcloud.admin.server.core.service.SysMenuService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.company.SysCompany;
import com.wust.springcloud.common.entity.sys.company.SysCompanyList;
import com.wust.springcloud.common.entity.sys.company.SysCompanySearch;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSource;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceList;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceSearch;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.exception.BusinessException;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.common.util.PinYin4jUtil;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by WST on 2019/6/17.
 */
@Deprecated
@Service("sysDataSourceServiceImpl")
public class SysDataSourceServiceImpl extends BaseServiceImpl implements SysDataSourceService {
    static Logger logger = LogManager.getLogger(SysDataSourceServiceImpl.class);

    @Autowired
    private SysDataSourceMapper sysDataSourceMapper;

    @Autowired
    private SysCompanyMapper sysCompanyMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SpringRedisTools springRedisTools;

    @Autowired
    private SysMenuService sysMenuServiceImpl;

    @Autowired
    private SysLookupService sysLookupServiceImpl;

    @Autowired
    private SysDataSourceService sysDataSourceService;


    @Autowired
    private Environment env;

    private static final String PLATFORM_DATABASE_NAME = "wust";

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysDataSourceMapper;
    }



    @Override
    public ResponseDto insert(SysDataSource entity) {
        ResponseDto messageMap = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysCompanySearch sysCompanySearch = new SysCompanySearch();
        List<SysCompanyList> sysCompanyLists = sysCompanyMapper.findByCondition(sysCompanySearch);
        SysCompany sysCompany = sysCompanyLists.get(0);

        String databaseName = PLATFORM_DATABASE_NAME + "_" +PinYin4jUtil.converterToFirstSpell(sysCompany.getName()) + "_" + UUID.randomUUID().toString().substring(0, 5);
        String username = databaseName;
        String password = UUID.randomUUID().toString().substring(0, 5);
        String loginName = sysUserMapper.getDefaultLoginName();
        String databaseInitDDLSql = "classpath:sql/database_init_ddl.sql";
        String databaseInitProdSql = "classpath:sql/database_init_prod.sql";
        String databaseInitDMLSql = "classpath:sql/database_init_dml.sql";

        Map parametersDDL = new HashMap();
        parametersDDL.put("database",databaseName);
        parametersDDL.put("username",username);
        parametersDDL.put("password",password);
        parametersDDL.put("platformDatabase",PLATFORM_DATABASE_NAME);

        Map parametersDML = new HashMap();
        parametersDML.put("database",databaseName);
        parametersDML.put("companyId",sysCompany.getId());
        parametersDML.put("loginName",loginName);
        parametersDML.put("realName",sysCompany.getName() + "系统管理员");
        parametersDML.put("createrId",ctx.getUserId());
        parametersDML.put("createrName",ctx.getLoginName());
        parametersDML.put("platformDatabase",PLATFORM_DATABASE_NAME);
        parametersDML.put("companyCode",sysCompany.getCode());
        parametersDML.put("companyName",sysCompany.getName());

        try {
            /**
             * 创建数据库
             */
            sysDataSourceMapper.createDataBase(parametersDDL);

            /**
             * 创建数据库账号
             */
            sysDataSourceMapper.createDataBaseUser(parametersDDL);

            /**
             * 执行SQL
             */
            executeSQL(databaseInitDDLSql,parametersDDL);
            executeSQL(databaseInitProdSql,parametersDDL);
            executeSQL(databaseInitDMLSql,parametersDML);


            /**
             * 平台新增数据源记录
             */
            entity.setJdbcDriver("com.mysql.cj.jdbc.Driver");
            entity.setJdbcUrl("jdbc:mysql://10.1.20.11:3306/" + databaseName + "?useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true");
            entity.setJdbcUsername(username);
            entity.setJdbcPassword(password);
            entity.setDescription(sysCompany.getName() + "的数据源");
            entity.setCreateTime(new Date());
            List<SysDataSource> entities = new ArrayList<>(1);
            entities.add(entity);
            sysDataSourceMapper.insertList(entities);



            /**
             * 平台也记录该用户
             */
            List<SysUser> sysUsers = new ArrayList<>(1);
            SysUser user = new SysUser();
            user.setLoginName(loginName);
            user.setRealName(sysCompany.getName() + "运营方管理员账号");
            user.setStatus("100201");
            user.setType("100403");
            user.setPassword("NDU3YzhjMWE0MWQ2");
            user.setCreateTime(new Date());
            sysUsers.add(user);
            sysUserMapper.insertList(sysUsers);

            /**
             * 重新缓存所有数据源（因为新增了数据源）
             */
            sysDataSourceService.cacheDataSource();

            /**
             * 为新数据源初始化数据
             */
            DefaultBusinessContext.getContext().setDataSourceId(entity.getCompanyId());
            DefaultBusinessContext.getContext().setCompanyId(entity.getCompanyId());
            sysMenuServiceImpl.init();
            sysLookupServiceImpl.init();
        }catch(Exception e){
            logger.error(e);
            sysDataSourceMapper.rollbackDataBase(parametersDDL);
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("初始化数据源失败");
            return messageMap;
        }
        return messageMap;
    }




    /**
     * 执行SQL
     * @param path
     */
    private void executeSQL(final String path,final Map parameters) {
        FileInputStream is = null;
        try {
            boolean isFuncFlag = false; // 是函数、存储过程标识
            File file = ResourceUtils.getFile(path);
            if (file != null && file.length() != 0) {
                is = new FileInputStream(file);
                InputStreamReader streamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    if(line.startsWith("--")
                            || line.startsWith("/*")
                            || "".equals(line)){
                        continue;
                    }

                    // 忽略分号标识
                    if(line.trim().toUpperCase().startsWith("DELIMITER")){
                        continue;
                    }

                    stringBuilder.append(line).append("\t");

                    if(line.trim().toUpperCase().startsWith("DROP") && line.endsWith(";")){ // 普通情况：删除语句，可以直接执行
                        parameters.put("sql",stringBuilder.toString());
                        sysDataSourceMapper.executeSQL(parameters);
                        stringBuilder = new StringBuilder();
                    }else{
                        if(line.trim().toUpperCase().startsWith("CREATE FUNCTION") || line.toUpperCase().startsWith("CREATE PROCEDURE")){
                            isFuncFlag = true;
                        }
                        if(isFuncFlag){
                            if(line.trim().equals(";;")){
                                parameters.put("sql",stringBuilder.toString());
                                sysDataSourceMapper.executeSQL(parameters);
                                stringBuilder = new StringBuilder();
                                isFuncFlag = false;
                            }
                        }else{
                            if (line.trim().endsWith(";")) { // 普通情况：其他的以分号结尾的都可以执行
                                parameters.put("sql",stringBuilder.toString());
                                sysDataSourceMapper.executeSQL(parameters);
                                stringBuilder = new StringBuilder();
                            }
                        }
                    }
                }
                reader.close();
                is.close();
            }
        } catch (Exception e) {
            throw new BusinessException("执行SQL["+parameters.get("sql")+"]失败");
        }
    }

    @Override
    public void cacheDataSource() {
        SysDataSourceSearch sysDataSourceSearch = new SysDataSourceSearch();
        List<SysDataSourceList> sysDataSourceLists = sysDataSourceMapper.findByCondition(sysDataSourceSearch);
        if(CollectionUtils.isNotEmpty(sysDataSourceLists)){
            JSONObject jsonObject = new JSONObject();
            for (SysDataSourceList sysDataSourceList : sysDataSourceLists) {
                jsonObject.put(sysDataSourceList.getCompanyId(),sysDataSourceList);
            }
            if(springRedisTools.hasKey(RedisKeyEnum.REDIS_TABLE_KEY_DATA_SOURCE.name())){
                springRedisTools.deleteByKey(RedisKeyEnum.REDIS_TABLE_KEY_DATA_SOURCE.name());
            }
            springRedisTools.addData(RedisKeyEnum.REDIS_TABLE_KEY_DATA_SOURCE.name(),jsonObject.toJSONString());
        }
    }
}
