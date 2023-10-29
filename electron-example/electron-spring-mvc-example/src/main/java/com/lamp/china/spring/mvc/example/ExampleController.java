package com.lamp.china.spring.mvc.example;

import java.util.ArrayList;
import java.util.List;

import com.lamp.electron.base.common.register.data.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.electron.base.common.RemotingUtil;
import com.lamp.electron.example.service.instance.InstanceInfoService;

@Slf4j
@RestController
@RequestMapping("/example")
public class ExampleController {

	@Reference(url="127.0.0.1:20000", check = false)
	private InstanceInfoService instanceInfoService;
	
	
	private String locadAddress = RemotingUtil.getLocalAddress();
	
	@PostMapping("addExample")
	public InstanceInfo addExample(@RequestBody InstanceInfo instanceInfo) {
		return instanceInfo;
	}
	
	@PostMapping("queryExample")
	public InstanceInfo queryExample() {
		return createExampleInfo();
	}
	
	@GetMapping("queryExampleList")
	public List<InstanceInfo> queryExampleList(){
		List<InstanceInfo> list = new ArrayList<InstanceInfo>();
		for(int i = 0; i< 10 ; i++) {
			list.add(createExampleInfo());
		}
		log.info("queryExampleList catch");
		return list;
	}
	
	private InstanceInfo createExampleInfo() {
		InstanceInfo instanceInfo = new InstanceInfo();
		instanceInfo.setApplicationId(1L);
		instanceInfo.setApplicationEnglishName("spring mvc example");
		instanceInfo.setNetworkAddress(locadAddress);
		return instanceInfo;
	}
	
}
