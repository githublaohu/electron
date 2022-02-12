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
package com.lamp.electron.base.common.ability;

import com.lamp.electron.base.common.annotation.AbiltiyData;
import com.lamp.electron.base.common.enums.AbiltiyScope;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;

import lombok.Data;

@Data
@AbiltiyData(abiltiyScope = AbiltiyScope.RPC, chinaName = "接口集", abiltityBindRelation = OrganizationTypeEnum.INTERFACE)
public class Interfaces {

}
