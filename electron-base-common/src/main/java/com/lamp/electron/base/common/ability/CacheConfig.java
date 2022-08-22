package com.lamp.electron.base.common.ability;

import java.util.List;

import com.lamp.electron.base.common.annotation.AbiltiyData;
import com.lamp.electron.base.common.enums.AbiltiyScope;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;

import lombok.Data;

@Data
@AbiltiyData(abiltiyScope = AbiltiyScope.INVOK, chinaName = "缓存结果", abiltityBindRelation = OrganizationTypeEnum.INTERFACE)
public class CacheConfig {

	private boolean usrKey;
	
	private List<KeyConfig> getKey;
	
	private List<KeyConfig> setKey;
	
	private long cacheTime;
	
	
	@Data
	private class KeyConfig{
		
		DataSpot dataSpot;
		
		String keyName;
		
		String defaultValue;
	}
}
