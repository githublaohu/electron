package com.lamp.electron.example.register;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {

	
	
	private String locadAddress = "register";
	
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
		return list;
	}
	
	private InstanceInfo createExampleInfo() {
		InstanceInfo instanceInfo = new InstanceInfo();
		instanceInfo.setId(1L);
		instanceInfo.setName("spring mvc example");
		instanceInfo.setNetworkAddress(locadAddress);
		return instanceInfo;
	}
	
}
