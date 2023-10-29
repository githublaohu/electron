package com.lamp.electron.base.common.cache;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class CacheService {

	private ConcurrentHashMap<String, CacheInfo> cacheMap = new ConcurrentHashMap<>();
	
	public void put(String key , Object value, long overtime,boolean fixed) {
		
		CacheInfo cacheInfo = new CacheInfo();
		cacheInfo.value = value;
		cacheInfo.expireTime = fixed? overtime : System.currentTimeMillis() + overtime;
		
		cacheMap.put(key, cacheInfo);
		
	}
	
	@SuppressWarnings("unchecked")
	public <T>T get(String key){
		CacheInfo cacheInfo=cacheMap.get(key);		
		return (T)(Objects.nonNull(cacheInfo) ?cacheInfo.value : null);
	}
	
	
	private class CacheInfo{
		
		private Object value;
		
		private long expireTime;
		
	}
}
