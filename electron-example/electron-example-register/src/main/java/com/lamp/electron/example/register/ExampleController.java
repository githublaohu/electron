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
		exampleInfo.setId(1L);
		exampleInfo.setName("spring mvc example");
		exampleInfo.setNetworkAddress(locadAddress);
		return exampleInfo;
	}
	
}
