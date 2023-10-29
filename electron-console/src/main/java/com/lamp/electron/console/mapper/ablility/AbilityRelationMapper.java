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
package com.lamp.electron.console.mapper.ablility;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.lamp.electron.base.common.register.data.AbilityRelation;

@Mapper
public interface AbilityRelationMapper {

	static final String table_name = " ability_relation ";

	static final String INSERT_SQL = "insert into ability_relation";

	static final String UPDATE_SQL = "update " + table_name + " set ";

	static final String SELECT_SQL = "select * from " + table_name + " where ";

	@Select({"<script>", SELECT_SQL, " ar_id =#{arId} "+
			"<if test='arRelationStatus != null'>  and ar_relation_status = #{arRelationStatus} </if>", 
			"</script>" })
	public AbilityRelation queryAbilityRelationById(AbilityRelation abilityRelation);

	@Select({ SELECT_SQL, " ai_id =#{aiId}" })
	public List<AbilityRelation> queryAbilityRelationListByAiId(AbilityRelation abilityRelation);

	@Select({ "<script>",
		SELECT_SQL, 
		" organization_id = #{organizationId} ",
		" <if test='abilityTypeEnum != null'> and ability_type_enum = #{abilityTypeEnum}  </if>",
		"</script>" })
	public List<AbilityRelation> queryAbilityRelationListByOrganizationId(AbilityRelation abilityRelation);

	@Update({ UPDATE_SQL, "ar_relation_status = #{arRelationStatus} where ar_id = #{abilityRelation.arId}" })
	public Integer updateAbilityInfoStatus(@Param("abilityRelation")AbilityRelation abilityRelation, @Param("arRelationStatus") Integer arRelationStatus);

	@Insert({ INSERT_SQL,
			"(ai_id,ai_name,ability_type_enum,protocel_config_enum,organization_id,organization_name,organization_type_enum,ar_explain,ar_relation_status)",
			"select ai_id,ai_name,ability_type_enum,protocel_config_enum,organization_id,organization_name,organization_type_enum,ar_explain,3",
			"from ability_relation where ar_id =#{arId}" })
	public Integer copyAbilityRelation(AbilityRelation abilityRelation);

	@Options(useGeneratedKeys = true, keyProperty = "arId", keyColumn = "ar_id")
	@Insert({ INSERT_SQL,
			"(ai_id,ai_name,ability_type_enum,organization_id,organization_name,organization_type_enum,protocel_config_enum,ar_explain)",
			"values(#{aiId},#{aiName},#{abilityTypeEnum},#{organizationId},#{organizationName},#{organizationTypeEnum},#{protocolConfigEnum},#{arExplain})" })
	public Integer insertAbilityRelation(AbilityRelation abilityRelation);

}
