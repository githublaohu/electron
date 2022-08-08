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
public interface InstanceAndInterfaceMapper {

	static final String table_name = "<if test='nodeTypeEnum == INSTANCE'> instance_info  </if>"
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
			"insert into instance_info(application_id , application_english_name,organization_type_enum,networkAddress,port,protocol,language,instance_type,rpc_type,version,client_version,create_time)",
			"values(#{applicationId},#{applicationEnglishName},#{organizationTypeEnum},#{networkAddress},#{port},#{protocol},#{language},#{instanceType},#{RPCType},#{version},#{clientVersion},#{gaterDate})",
	})
	public Integer insertNodeBase(NodeBase nodeBase);

	@Insert({
			"insert into  interface_info (application_id,application_english_name,organization_type_enum,networkAddress,port,protocol,language,instance_type,rpc_type,version,client_version,path,http_method_type,module_name,interace_name,method_name,class_name,create_time)",
			"values(#{applicationId},#{applicationEnglishName},#{organizationTypeEnum},#{networkAddress},#{port},#{protocol},#{language},#{instanceType},#{RPCType},#{version},#{clientVersion},#{path},#{httpMethodTypeString},'electron','interaceName',#{methodName},#{className},#{gaterDate})",
	})
	public Integer insertInterface(NodeBase nodeBase);
	
	/**
	 * 修改
	 * 
	 * @param nodeBase
	 * @return
	 */
	@Update({UPDATE_SQL , "is_delete=1","where network_address = #{networkAddress} and create_time = #{createTime}"})
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
	@Select({"select * from instance_info where application_english_name = #{organizationEnglishName} order by create_time desc"})
	public List<NodeBase> queryNodeBaseListByFrom(NodeBase nodeBase);

	@ResultType(InterfaceInfo.class)
	@Select({"select * from interface_info where application_english_name = #{organizationEnglishName} = #{organizationId}"})
	public List<NodeBase> queryInterfaceInfoListByFrom(NodeBase nodeBase);
 
}
