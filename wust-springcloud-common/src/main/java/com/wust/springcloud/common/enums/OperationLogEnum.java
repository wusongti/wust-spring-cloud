package com.wust.springcloud.common.enums;

/**
 * Created by WST on 2019/5/27.
 */
public enum OperationLogEnum {
    Insert("新增"),
    Update("修改"),
    Delete("删除"),
    Search("查询"),
    Save("保存"),
    Download("下载"),
    Upload("上传"),
    Import("导入"),
    Export("导出"),
    Login("登录"),
    Logout("注销"),
    MODULE_COMMON("通用模块"),
    MODULE_ADMIN_APP_TOKEN("外部应用访问Token管理模块"),
    MODULE_ADMIN_COMPANY("公司模块"),
    MODULE_ADMIN_DATA_SOURCE("数据源模块"),
    MODULE_ADMIN_DEPARTMENT("部门模块"),
    MODULE_ADMIN_ROLE("角色模块"),
    MODULE_ADMIN_USER("用户模块"),
    MODULE_ADMIN_ORGANIZATION("组织架构模块"),
    MODULE_ADMIN_LOOKUP("数据字典模块"),
    MODULE_ADMIN_IMPORT_EXPORT("导入导出模块"),
    MODULE_ADMIN_DATA_PRIVILEGE("数据权限模块");

    private String value;
    public String getValue() {
        return value;
    }
    OperationLogEnum(String value){
        this.value = value;
    }
}
