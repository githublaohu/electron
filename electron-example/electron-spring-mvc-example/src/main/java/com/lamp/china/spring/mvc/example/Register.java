package com.lamp.china.spring.mvc.example;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;

@Component
public class Register {

	@Value("${nacos.config.server-addr:#{null}}")
	private String nacosAddr;
	@Value("${nacos.config.namespace:public}")
	private String namespace;
	@Value("${server.port}")
	private String port;

	@Value("${ip:#{null}}")
	private String ip;

	NamingService namingService = null;

	public String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {

			return null;
		}
	}

	
	@PostConstruct
	public void init() throws NacosException {
		if(Objects.isNull(ip)) {
			this.ip = getIp();
		}
		if (Objects.nonNull(this.nacosAddr)) {
			this.initNacos();
		}

	}

	private void initNacos() throws NacosException {
		Properties properties = new Properties();
		properties.put("serverAddr", this.nacosAddr);
		properties.put("namespace", this.namespace);
		namingService = NamingFactory.createNamingService(properties);
	}

	@Scheduled(fixedRate = 5000)
	public void registerService() throws NacosException {
		if (Objects.nonNull(namingService)) {
			namingService.registerInstance("nacos-test-springmvc", ip, Integer.parseInt(port));
		}
	}
}
