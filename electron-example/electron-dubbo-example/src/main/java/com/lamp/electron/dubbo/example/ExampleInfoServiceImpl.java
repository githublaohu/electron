package com.lamp.electron.dubbo.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;

import com.lamp.electron.base.common.RemotingUtil;
import com.lamp.electron.base.common.register.data.ExampleInfo;
import com.lamp.electron.example.service.example.ExampleInfoService;

@DubboService
public class ExampleInfoServiceImpl implements ExampleInfoService {
	
	private String locadAddress = RemotingUtil.getLocalAddress();

	public ExampleInfo addExample(ExampleInfo exampleInfo) {
		return exampleInfo;
	}

	public ExampleInfo queryExample() {
		return createExampleInfo();
	}

	public List<ExampleInfo> queryExampleList() {
		List<ExampleInfo> list = new ArrayList<ExampleInfo>();
		for (int i = 0; i < 10; i++) {
			list.add(createExampleInfo());
		}
		return list;
	}

	private ExampleInfo createExampleInfo() {
		ExampleInfo exampleInfo = new ExampleInfo();
		exampleInfo.setApplicationId(1L);
		exampleInfo.setApplicationEnglishName("dubbo example");
		exampleInfo.setNetworkAddress(locadAddress);
		return exampleInfo;
	}

}
