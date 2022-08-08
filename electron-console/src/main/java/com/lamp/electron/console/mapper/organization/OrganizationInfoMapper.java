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
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lamp.electron.console.entity.organization.OrganizationInfo;

@Mapper
public interface OrganizationInfoMapper {

	/**
	 * <code>
		  create table  organization_info(
		      oi_id bigint unsigned not null auto_increment,
		      oi_superior_id bigint unsigned not null default 0 comment '附id',
		      oi_name varchar(31) not null comment'组织名',
		      oi_english_name varchar(31) not null comment '组织英语名',
		      oi_type varchar(31) not null comment '组织类型[ 团队，业务线，项目，环境 四个]',
		      oi_tag json not null default "[]" comment "标签",
		      oi_explain varchar(127) not null comment '说明',
		      oi_subordinate_num int unsigned not null default 0 comment '下级数量',
		      oi_interface_num int unsigned not null default 0 comment 'api',
		      oi_creater_id bigint unsigned not null comment'创建人',
		      oi_creater_name varchar(31) not null comment'创建人名',
		      oi_owner_id bigint unsigned not null comment'所有权人',
		      oi_owner_name varchar(31) not null comment'所有权名',
		      create_time datetime not null default current_timestamp comment '创建时间',
		      create_founder bigint unsigned not null default 0 comment '', 
		      update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间', 
		      update_founder bigint unsigned not null default 0 comment '', 
		      is_delete int unsigned not null default 0  comment '数据状态',
		      primary key (`oi_id`)
	  	  );
	 * </code>
	 */
	static String TABLE_SQL = "organization_info";

	static String INSET_SQL = "insert into " + TABLE_SQL
			+ "(oi_superior_id,oi_name,oi_english_name,oi_type,oi_explain,oi_creater_id,oi_creater_name,oi_owner_id,oi_owner_name)values";

	static String UPDATE_SQL = "update " + TABLE_SQL + " set ";

	static String SELECT_SQL = "select * from " + TABLE_SQL + " where ";

	@Select({
		"<script>",
		"select * from (",
		SELECT_SQL , "oi_owner_id = #{oiOwnerId} and is_delete = 0",
		"union all",
		SELECT_SQL , "oi_id in (select oi_id from organization_power where ui_id = #{oiOwnerId}  and is_delete = 0)  and is_delete = 0",
		") as t",
		"</script>"
	})
	List<OrganizationInfo> queryOrganizationInfoByUserId(OrganizationInfo organizationInfo);
	
	@Select(SELECT_SQL + " oi_id = #{oiId} ")
	OrganizationInfo queryOrganizationInfoById(OrganizationInfo organizationInfo);

	@Select(SELECT_SQL + " oi_superior_id = #{oiSuperiorId} and is_delete = 0 ")
	List<OrganizationInfo> queryOrganizationInfoByTypeAndSuperior(OrganizationInfo organizationInfo);

	@Update(UPDATE_SQL + " oi_explain = #{oiExplain} where oi_id = #{oiId} ")
	Integer increaseSubordinateNum(OrganizationInfo organizationInfo);

	@Update(UPDATE_SQL + " oi_subordinate_num = #{oiExplain} where oi_id = #{oiId} ")
	Integer decreaseSubordinateNum(OrganizationInfo organizationInfo);

	@Update(UPDATE_SQL + "oi_explain = #{oiExplain} where oi_id = #{oiId}")
	Integer updateSuperiorIdById(OrganizationInfo organizationInfo);

	@Update(UPDATE_SQL + "oi_owner_id = #{oiOwnerId} , oi_owner_name = #{oiOwnerName} where oi_id = #{oiId}")
	Integer updateOwnerById(OrganizationInfo organizationInfo);

	@Update(UPDATE_SQL + "oi_explain = #{oiExplain} where oi_id = #{oiId}")
	Integer updateExplainById(OrganizationInfo organizationInfo);
	
	@Update(UPDATE_SQL + "oi_subordinate_num = oi_subordinate_num+(#{oiSubordinateNum}) where oi_id = #{oiSuperiorId}")
	Integer updateSubordinateNumById(OrganizationInfo organizationInfo);

	@Update(UPDATE_SQL + " is_delete = 1  where oi_id = #{oiId} and oi_subordinate_num = 0")
	Integer deleteOrganizationById(OrganizationInfo organizationInfo);

	@Insert(INSET_SQL
			+ "(#{oiSuperiorId},#{oiName},#{oiEnglishName},#{oiType},#{oiExplain},#{oiCreaterId},#{oiCreaterName},#{oiOwnerId},#{oiOwnerName})")
	@Options(useGeneratedKeys = true, keyProperty = "oiId")
	Integer insertOrganizationInfo(OrganizationInfo organizationInfo);

}