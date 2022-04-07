package com.lamp.electron.example.register;


import lombok.Data;

@Data
public class InstanceInfo {

	private Long id;

	private String name;

	private String networkAddress;

	private String port;

	private String language;

	private String instanceType;

	private String RPCType;

	private String version;

	
	private String registryCentre;
	
	private String realize;
	
}
