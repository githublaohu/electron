package com.lamp.china.spring.mvc.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.electron.base.common.RemotingUtil;
import com.lamp.electron.base.common.register.data.ExampleInfo;
import com.lamp.electron.example.service.example.ExampleInfoService;

@RestController
@RequestMapping("/example")
public class ExampleController {

	@Reference(url="127.0.0.1:20000", check = false)
	private ExampleInfoService exampleInfoService;
	
	
	private String locadAddress = RemotingUtil.getLocalAddress();
	
	@PostMapping("addExample")
	public ExampleInfo addExample(@RequestBody ExampleInfo exampleInfo) {
		return exampleInfo;
	}
	
	@PostMapping("queryExample")
	public ExampleInfo queryExample() {
		return createExampleInfo();
	}
	
	@GetMapping("queryExampleList")
	public List<ExampleInfo> queryExampleList(){
		List<ExampleInfo> list = new ArrayList<ExampleInfo>();
		for(int i = 0; i< 10 ; i++) {
			list.add(createExampleInfo());
		}
		return list;
	}
	
	private ExampleInfo createExampleInfo() {
		ExampleInfo exampleInfo = new ExampleInfo();
		exampleInfo.setApplicationId(1L);
		exampleInfo.setApplicationEnglishName("spring mvc example");
		exampleInfo.setNetworkAddress(locadAddress);
		return exampleInfo;
	}
	
}
