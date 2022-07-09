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

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lamp.electron.base.common.register.data.AbilityInfo;

@Mapper
public interface AbilityInfoMapper {

	@Insert({"insert into ability_info",
			" (organization_id,organization_name,organization_type_enum,ai_parent_id, ai_name,ai_lable,ai_ability_type,ai_data,ai_relation_status)",
			" values(#{organizationId},#{organizationName},#{organizationTypeEnum},#{aiParentId},#{aiName},#{aiLabel},#{aiAbilityType},#{aiData},#{aiRelationStatus})"})
	public Integer insertAbilityInfo(AbilityInfo abilityInfo);

	@Update({"<script>",
		    "update ability_info set ai_relation_status = 2 where ai_id = #{aiId}",
			"		<if test='aiParentId == 0'>  and ai_bind_time = 0 </if>",
			"</script>"
		})
	public Integer updateAbilityInfoStatus(AbilityInfo abilityInfo);
	
	@Update("update ability_info set ai_bind_time = ai_bind_time + 1 where ai_id = #{aiId}")
    public Integer bindIncrease(AbilityInfo strategyInfo);

	@Update("update ability_info set ai_bind_time = ai_bind_time - 1 where ai_id = #{aiId}")
    public Integer bindDecrease(AbilityInfo strategyInfo);

    @Select({"select * from ability_info where ",
    		" organization_id = #{organizationId} and ",
    		" organization_type_enum = #{organizationTypeEnum} and ",
    		" ai_ability_type = #{aiAbilityType} and",
    		" ai_parent_id = #{aiParentId}"
    	})
	public List<AbilityInfo> queryAbilityInfoByType(AbilityInfo abilityInfo);

    @Select("select * from ability_info where ai_parent_id = #{aiParentId}")
	public List<AbilityInfo> queryAbilityInfoByParentId(AbilityInfo abilityInfo);
    
    @Select("select * from ability_info where ai_id = #{aiId}")
    public AbilityInfo queryAbilityInfoById(AbilityInfo abilityInfo);

}
