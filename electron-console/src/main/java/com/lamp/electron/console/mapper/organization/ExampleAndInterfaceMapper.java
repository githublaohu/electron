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
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lamp.electron.base.common.basedata.NodeBase;
import com.lamp.electron.base.common.register.data.InterfaceInfo;

@Mapper
public interface ExampleAndInterfaceMapper {

	static final String table_name = "<if test='nodeTypeEnum == EXAMPLE'> example_info  </if>"
			+ "<if test='nodeTypeEnum == INTERFACE'> interface_info  </if>";

	static final String SELECT_SQL = "select * from " + table_name + " where ";

	static final String UPDATE_SQL = "update " + table_name + " set ";

	static final String INSERT_SQL = "insert into " + table_name;

	/**
	 * 
	 * @param nodeBase
	 * @return
	 */
	@Insert({
		INSERT_SQL,"()value()"
	})
	public Integer insertNodeBase(NodeBase nodeBase);

	/**
	 * 修改
	 * 
	 * @param nodeBase
	 * @return
	 */
	@Update({UPDATE_SQL , "is_detele=1","where network_address = #{networkAddress} and create_time = #{createTime}"})
	public Integer updateNodeBase(NodeBase nodeBase);

	/**
	 * 
	 * @param nodeBase
	 * @return
	 */
	@ResultType(InterfaceInfo.class)
	@Select({"select * from ",SELECT_SQL,"id = #{id}"})
	public NodeBase queryNodeBaseById(NodeBase nodeBase);

	/**
	 * 
	 * @param nodeBase
	 * @return
	 */
	@ResultType(InterfaceInfo.class)
	@Select({"<script>",
			SELECT_SQL,
			"<if test='#{organizationTypeEnum} == TEAM'> "
			+ "application_id in( select oi_id  from organization_info where oi_superior_id = #{organizationId}) "
			+ "</if>",
			"<if test='#{organizationTypeEnum} == APPLICATION'> application_id = #{organizationId} </if>",
			"</script>"})
	public List<NodeBase> queryNodeBaseListByOiId(NodeBase nodeBase);

	/**
	 * 
	 * @param nodeBase
	 * @return
	 */
	@ResultType(InterfaceInfo.class)
	public List<NodeBase> queryNodeBaseListByFrom(NodeBase nodeBase);

}
