/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.electron.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElectronStartup {

	public static void main(String[] args) {
		start(createBrokerController(args));
	}

	public static void start(ElectronController controller) {
		try {

			controller.start();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	public static void shutdown(final ElectronController controller) {
		if (null != controller) {
			controller.shutdown();
		}
	}

	public static ElectronController createBrokerController(String[] args) {
		String rootPath = ElectronStartup.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			// 	读取配置文件electron.yaml
			ElectronConfig electronConfig = readYaml(rootPath + "electron.yaml");
	
			File configFile = new File(rootPath+"config");
			List<ElectronConfig> electronConfigList = null;
			if(configFile.exists() && configFile.isDirectory()) {
				electronConfigList = new ArrayList<>(configFile.list().length);
				for(File file : configFile.listFiles()) {
					if(file.getName().endsWith(".yaml")){
						ElectronConfig newEectronConfig = new Yaml().loadAs(new BufferedInputStream(new FileInputStream(file)), ElectronConfig.class);
						electronConfigList.add(newEectronConfig);
					}
				}
			}
			return new ElectronController(electronConfig, electronConfigList);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
			System.exit(1);
			return null;
		}
	}

	private static ElectronConfig readYaml(String path) throws FileNotFoundException {
		File file = new File(path);
		if (!file.exists()) {
			log.error("electorn.yaml not exists");
			System.exit(1);
		}

		Yaml yaml = new Yaml();
		return yaml.loadAs(new BufferedInputStream(new FileInputStream(file)), ElectronConfig.class);
	}

}
