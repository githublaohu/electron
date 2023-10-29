package com.lamp.electron.base.common.cache;

import java.util.concurrent.ConcurrentHashMap;


public class CacheManage {
	
	private ConcurrentHashMap<String, CacheService> cacheMap = new ConcurrentHashMap<>();
	
	public CacheService createCacheService;

}
