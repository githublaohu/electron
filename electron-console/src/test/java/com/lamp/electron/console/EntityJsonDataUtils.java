package com.lamp.electron.console;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.enums.ProtocolConfigEnum;
import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.console.entity.organization.OrganizationInfo;
import com.lamp.electron.console.entity.organization.OrganizationPower;
import com.lamp.electron.console.model.enums.OrganizationPowerEnums;

public class EntityJsonDataUtils {

	@Test
	public void toOrganizationInfo() {
		OrganizationInfo organizationInfo = new OrganizationInfo();
		organizationInfo.setOiName("测试项目");
		organizationInfo.setOiEnglishName("test-organization");
		organizationInfo.setOiSuperiorId(1L);
		organizationInfo.setOiType(OrganizationTypeEnum.SPACE);
		organizationInfo.setOiExplain("test-test-tetete");
		System.out.println(JSON.toJSONString(organizationInfo));
	}

	@Test
	public void toOrganizationPower() {
		OrganizationPower organizationPower = new OrganizationPower();
		organizationPower.setOrganizationId(1L);
		organizationPower.setOrganizationName("testtt");
		organizationPower.setOrganizationEnglistName("dfasdfafaff");
		organizationPower.setOrganizationTypeEnum(OrganizationTypeEnum.SPACE);
		organizationPower.setUiId(1L);
		organizationPower.setUiName("1231111");
		organizationPower.setOpPower(OrganizationPowerEnums.ADMINISTRATOR);
		System.out.println(JSON.toJSONString(organizationPower));
	}

	@Test
	public void toAbilityRelation() throws NoSuchMethodException, SecurityException {
		AbilityRelation abilityRelation = new AbilityRelation();
		abilityRelation.setAiId(1L);
		abilityRelation.setAiName("hello");
		abilityRelation.setAbilityTypeEnum(AbilityTypeEnum.CONFIG);
		abilityRelation.setProtocelConfigEnum(ProtocolConfigEnum.NONE);
		abilityRelation.setOrganizationId(1L);
		abilityRelation.setOrganizationId(1L);
		abilityRelation.setOrganizationName("1号");
		abilityRelation.setOrganizationTypeEnum(OrganizationTypeEnum.SPACE);
		abilityRelation.setArExplain("11111111111");
		abilityRelation.setAbility("{}");
		System.out.println(JSON.toJSONString(abilityRelation));
	}

}
