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
package com.lamp.electron.base.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.enums.AbilityScope;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.viewmodel.BehaviorOperation;
import com.lamp.electron.base.common.viewmodel.DataModeBehavior;
import com.lamp.electron.base.common.viewmodel.ViewModel;

import lombok.Data;
import lombok.Getter;

public class AbilityTypeEnumAnalysis {

	private Map<AbilityScope, TreeModel> abilityScopeByTreeModel = new HashMap<>();

	private List<TreeModel> treeModelList = new ArrayList<>();

	@Getter
	private String treeModeJson;
	
	public void analysis() {

		for (AbilityScope abilityScope : AbilityScope.values()) {
			if (abilityScope.isTree()) {
				TreeModel treeModel = new TreeModel();
				treeModel.setExplain(abilityScope.getExplain());
				treeModel.setLabel(abilityScope.getLabel());
				abilityScopeByTreeModel.put(abilityScope, treeModel);
				treeModelList.add(treeModel);
			}
		}

		for (AbilityTypeEnum abilityTypeEnum : AbilityTypeEnum.values()) {
			this.treeAbility(abilityTypeEnum);
			this.viewModel(abilityTypeEnum);
		}
		
		this.treeModeJson = JSON.toJSONString(treeModelList);
	}
	
	public void viewModel(AbilityTypeEnum abilityTypeEnum) {
		Class<?> clazz = abilityTypeEnum.getAbilityObject();
		
		ViewModel viewModel = clazz.getAnnotation(ViewModel.class);
		
		for(Field filed : clazz.getDeclaredFields()) {
			BehaviorOperation behaviorOperation = filed.getAnnotation(BehaviorOperation.class);
			DataModeBehavior[] dataModeBehavior = filed.getAnnotationsByType(DataModeBehavior.class);
		}
	}
	
	public void treeAbility(AbilityTypeEnum abilityTypeEnum) {
		TreeModel treeModel = abilityScopeByTreeModel.get(abilityTypeEnum.getAbilityScope()[0]);
		TreeModel chlidren = new TreeModel();
		chlidren.setLabel(abilityTypeEnum.getChinaName());
		chlidren.setExplain(abilityTypeEnum.getExplain());
		chlidren.setType(abilityTypeEnum.name());
		String path = (new StringBuilder()).append(Character.toLowerCase(abilityTypeEnum.getName().charAt(0))).append(abilityTypeEnum.getName().substring(1)).toString();
		chlidren.setResourcePath(path);
		List<String> relation = new ArrayList<>();
		for(OrganizationTypeEnum organizationTypeEnum : abilityTypeEnum.getOrganizationTypeEnum()) {
			relation.add(organizationTypeEnum.name());
		}
		chlidren.setRelation(relation);
		List<TreeModel> childrenlist = treeModel.getChildrenlist();
		if (Objects.isNull(childrenlist)) {
			childrenlist = new ArrayList<>();
			treeModel.setChildrenlist(childrenlist);
		}
		childrenlist.add(chlidren);
	}

	@Data
	static class TreeModel {
		private String label;

		private String type;
		
		private String resourcePath;

		private String explain;

		private List<TreeModel> childrenlist;
		
		private List<String> relation;
	}
	
	public static void main(String[] args) {
		AbilityTypeEnumAnalysis abilityTypeEnumAnalysis = new AbilityTypeEnumAnalysis();
		abilityTypeEnumAnalysis.analysis();
		System.out.println(abilityTypeEnumAnalysis.getTreeModeJson().replace("\"", "\\\""));
	}
}
