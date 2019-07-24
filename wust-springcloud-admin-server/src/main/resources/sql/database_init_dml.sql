INSERT INTO `sys_user` (
	`id`,
	`login_name`,
	`real_name`,
	`sex`,
	`email`,
	`mobile`,
	`password`,
	`status`,
	`type`,
	`company_id`,
	`creater_id`,
	`creater_name`,
	`create_time`
)
VALUES
(
    UUID(),
    #{loginName},
    #{realName},
    '100302',
    NULL,
    NULL,
    'NDU3YzhjMWE0MWQ2',
    '100201',
    '100403',
    #{companyId},
    #{createrId},
    #{createrName},
    SYSDATE()
);



INSERT INTO `sys_company`
(
	`id`,
	`code`,
	`pcode`,
	`name`,
	`leader`,
	`description`,
	`creater_id`,
	`creater_name`,
	`create_time`
)
VALUES
(
	#{companyId},
	#{companyCode},
	NULL,
	#{companyName},
	'',
	'',
    #{createrId},
    #{createrName},
	SYSDATE()
);