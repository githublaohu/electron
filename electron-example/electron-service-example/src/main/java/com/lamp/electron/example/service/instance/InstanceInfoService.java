package com.lamp.electron.example.service.instance;

import java.util.List;

import com.lamp.electron.base.common.register.data.InstanceInfo;

public interface InstanceInfoService {

	/**
	 * 增加实例
	 * @param instanceInfo
	 * @return
	 */
	public InstanceInfo addInstance(InstanceInfo instanceInfo);

	/**
	 * 查询实例
	 * @return
	 */
	public InstanceInfo queryInstance();

	/**
	 * 查询实例列表
	 * @return
	 */
	public List<InstanceInfo> queryInstanceList();

}
