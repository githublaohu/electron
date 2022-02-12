package com.lamp.electron.example.service.example;

import java.util.List;

import com.lamp.electron.base.common.register.data.ExampleInfo;

public interface ExampleInfoService {

	public ExampleInfo addExample(ExampleInfo exampleInfo);

	public ExampleInfo queryExample();

	public List<ExampleInfo> queryExampleList();

}
