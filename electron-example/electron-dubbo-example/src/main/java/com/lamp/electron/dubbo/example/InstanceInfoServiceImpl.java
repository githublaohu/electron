package com.lamp.electron.dubbo.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;

import com.lamp.electron.base.common.RemotingUtil;
import com.lamp.electron.base.common.register.data.InstanceInfo;
import com.lamp.electron.example.service.instance.InstanceInfoService;

@DubboService
public class InstanceInfoServiceImpl implements InstanceInfoService {
	
	private String locadAddress = RemotingUtil.getLocalAddress();

	@Override
	public InstanceInfo addInstance(InstanceInfo instanceInfo) {
		return instanceInfo;
	}

	@Override
	public InstanceInfo queryInstance() {
		return createExampleInfo();
	}

	@Override
	public List<InstanceInfo> queryInstanceList() {
		List<InstanceInfo> list = new ArrayList<InstanceInfo>();
		for (int i = 0; i < 10; i++) {
			list.add(createExampleInfo());
		}
		return list;
	}

	private InstanceInfo createExampleInfo() {
		InstanceInfo instanceInfo = new InstanceInfo();
		instanceInfo.setApplicationId(1L);
		instanceInfo.setApplicationEnglishName("dubbo example");
		instanceInfo.setNetworkAddress(locadAddress);
		return instanceInfo;
	}

}
