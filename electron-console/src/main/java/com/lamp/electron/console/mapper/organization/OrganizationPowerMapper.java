/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.electron.console.mapper.organization;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lamp.electron.console.entity.organization.OrganizationPower;

@Mapper
public interface OrganizationPowerMapper {

	/**
	 * <code>
	create table organization_power(
		op_id  bigint unsigned not null auto_increment,
		ui_id bigint unsigned not null comment '用户id',
		ui_name varchar(36) not null comment '用户名',
		organization_id bigint unsigned not null comment '组织id',
		organization_name  varchar(31) not null comment '组织名',
		organization_type_enum varchar(31) not null comment '组织名',
		op_power varchar(36) not null comment '角色',
		op_status int not null default 0 comment '数据状态',
		create_time datetime not null default current_timestamp comment '创建时间',
		create_founder bigint unsigned not null default 0 comment '', 
		update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间', 
		update_founder bigint unsigned not null default 0 comment '', 
		is_detele int unsigned not null default 0  comment '数据状态',
		primary key (`op_id`)
	)
	 * </code>
	 */
	static final String table_name = " organization_power ";

	static final String SECECT_SQL = "select * from " + table_name + " where ";

	static final String UPDATE_SQL = "update " + table_name + " set ";

	@Insert("insert into " + table_name
			+ "(ui_id,ui_name,organization_id,organization_name,organization_type_enum,op_power)values(#{uiId},#{uiName},#{organizationId},#{organizationName},#{organizationTypeEnum},#{opPower})")
	@Options(useGeneratedKeys = true, keyProperty = "opId")
	Integer insertOrganizationPower(OrganizationPower organizationPower);

	@Update(UPDATE_SQL + " op_status = #{opStatus}  where op_id = #{opId}")
	Integer updateOrganizationPowerStatusByOpId(OrganizationPower organizationPower);

	@Update(UPDATE_SQL + " op_power = #{opPower}  where op_id = #{opId}")
	Integer updatePowerOpId(OrganizationPower organizationPower);

	@Select(SECECT_SQL + "organization_id = #{organizationId} and organization_type_enum = #{organizationTypeEnum}")
	@ResultType(OrganizationPower.class)
	List<OrganizationPower> queryOrganizationPowerByOiId(OrganizationPower organizationPower);
}
