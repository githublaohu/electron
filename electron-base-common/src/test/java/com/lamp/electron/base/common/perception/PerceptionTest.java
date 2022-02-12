package com.lamp.electron.base.common.perception;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lamp.electron.base.common.ability.config.http.HttpRequestConfig;

/**
 * 分四个层，第一层1个节点，第二层2个节点，第三次4个节点，第四层
 * 
 * @author laohu
 *
 */
public class PerceptionTest {

	private Field childPerception;

	private Field parentPerception;

	private Field currentJSONObject;

	private Field perceptionJSONObject;

	@Before
	public void before() throws NoSuchFieldException, SecurityException {
		childPerception = Perception.class.getDeclaredField("childPerception");
		childPerception.setAccessible(true);
		parentPerception = Perception.class.getDeclaredField("parentPerception");
		parentPerception.setAccessible(true);
		perceptionJSONObject = Perception.class.getDeclaredField("perceptionJSONObject");
		perceptionJSONObject.setAccessible(true);
		currentJSONObject = Perception.class.getDeclaredField("currentJSONObject");
		currentJSONObject.setAccessible(true);
	}

	@Test
	public void aloneTest() {
		Perception<Object> perception = new Perception<>();
		Assert.assertEquals(perception.getPerceptionTypeEnum(), PerceptionTypeEnum.ALONE);

		Object object = new Object();
		perception.setPereptionObject(object);
		Assert.assertEquals(object, perception.pereptionObject());
	}

	@Test
	public void rootPerceptionTest() throws IllegalArgumentException, IllegalAccessException {
		Perception<Object> perception = new Perception<>(null);

		Assert.assertNull(perception.pereptionObject());
		Assert.assertNull(parentPerception.get(perception));
		Assert.assertNotNull(childPerception.get(perception));
		JSONObject currentJSONObject = (JSONObject) this.currentJSONObject.get(perception);
		Assert.assertTrue(currentJSONObject.isEmpty());

		HttpRequestConfig httpRequestConfig = new HttpRequestConfig();
		perception = new Perception<>(httpRequestConfig);

		Assert.assertNotNull(perception.pereptionObject());
		Assert.assertNull(parentPerception.get(perception));
		Assert.assertNotNull(childPerception.get(perception));
		currentJSONObject = (JSONObject) this.currentJSONObject.get(perception);
		Assert.assertFalse(currentJSONObject.isEmpty());
	}

	@Test
	public void childNodeTest() throws IllegalArgumentException, IllegalAccessException {
		Perception<HttpRequestConfig> parentNullDataPerception = new Perception<>(null);

		Perception<HttpRequestConfig> childNullNataPerception = new Perception<>(parentNullDataPerception, (HttpRequestConfig)null);
		HttpRequestConfig httpRequestConfig = childNullNataPerception.pereptionObject();
		Assert.assertNull(httpRequestConfig);
		Assert.assertNotNull(childPerception.get(childNullNataPerception));
		Assert.assertNotNull(parentPerception.get(childNullNataPerception));
		JSONObject currentJSONObject = (JSONObject) this.currentJSONObject.get(childNullNataPerception);
		Assert.assertTrue(currentJSONObject.isEmpty());

		HttpRequestConfig childHttpRequestConfig = new HttpRequestConfig();
		childHttpRequestConfig.setRequestTimeout(1000);
		childHttpRequestConfig.setFollowRedirect(true);
		childNullNataPerception = new Perception<>(parentNullDataPerception, childHttpRequestConfig);

		httpRequestConfig = childNullNataPerception.pereptionObject();
		Assert.assertNull(httpRequestConfig);
		Assert.assertNotNull(childPerception.get(childNullNataPerception));
		Assert.assertNotNull(parentPerception.get(childNullNataPerception));
		currentJSONObject = (JSONObject) this.currentJSONObject.get(childNullNataPerception);
		Assert.assertEquals(currentJSONObject.getInteger("requestTimeout"), childHttpRequestConfig.getRequestTimeout());
		Assert.assertEquals(currentJSONObject.getBoolean("followRedirect"), childHttpRequestConfig.isFollowRedirect());

		HttpRequestConfig parentHttpRequestConfig = new HttpRequestConfig();
		parentHttpRequestConfig.setRequestTimeout(1200);
		parentHttpRequestConfig.setFollowRedirect(false);
		parentHttpRequestConfig.setAccessControlAllowCredentials("*");
		Perception<HttpRequestConfig> parentDataPerception = new Perception<>(parentHttpRequestConfig);

		childNullNataPerception = new Perception<>(parentDataPerception, childHttpRequestConfig);

		JSONObject perceptionJSONObject = (JSONObject) this.perceptionJSONObject.get(childNullNataPerception);
		Assert.assertEquals(perceptionJSONObject.getInteger("requestTimeout"),
				childHttpRequestConfig.getRequestTimeout());
		Assert.assertEquals(perceptionJSONObject.getBoolean("followRedirect"),
				childHttpRequestConfig.isFollowRedirect());
		Assert.assertEquals(perceptionJSONObject.getString("accessControlAllowCredentials"),
				parentHttpRequestConfig.getAccessControlAllowCredentials());
	}

	@Test
	public void lastNode() {
		HttpRequestConfig parentHttpRequestConfig = new HttpRequestConfig();
		parentHttpRequestConfig.setRequestTimeout(1200);
		parentHttpRequestConfig.setFollowRedirect(false);
		parentHttpRequestConfig.setAccessControlAllowCredentials("*");
		Perception<HttpRequestConfig> parentDataPerception = new Perception<>(parentHttpRequestConfig);

		HttpRequestConfig childHttpRequestConfig = new HttpRequestConfig();
		childHttpRequestConfig.setRequestTimeout(1000);
		childHttpRequestConfig.setFollowRedirect(true);
		Perception<HttpRequestConfig> childNataPerception = new Perception<>(parentDataPerception,
				childHttpRequestConfig);

		HttpRequestConfig lastHttpRequestConfig = new HttpRequestConfig();
		lastHttpRequestConfig.setRequestTimeout(1300);
		lastHttpRequestConfig.setFollowRedirect(true);
		lastHttpRequestConfig.setAccessControlAllowHeaders("setAccessControlAllowHeaders");
		Perception<HttpRequestConfig> lastNodeDataPerception = new Perception<>(childNataPerception,
				lastHttpRequestConfig);

		HttpRequestConfig httpRequestConfig = lastNodeDataPerception.pereptionObject();
		Assert.assertEquals(httpRequestConfig.getRequestTimeout(), lastHttpRequestConfig.getRequestTimeout());
		Assert.assertEquals(httpRequestConfig.isFollowRedirect(), lastHttpRequestConfig.isFollowRedirect());
		Assert.assertEquals(httpRequestConfig.getAccessControlAllowCredentials(),
				parentHttpRequestConfig.getAccessControlAllowCredentials());
		Assert.assertEquals(httpRequestConfig.getAccessControlAllowHeaders(),
				lastHttpRequestConfig.getAccessControlAllowHeaders());
	}

	@Test
	public void setPerception() {
		HttpRequestConfig parentHttpRequestConfig = new HttpRequestConfig();
		parentHttpRequestConfig.setRequestTimeout(1200);
		parentHttpRequestConfig.setFollowRedirect(false);
		parentHttpRequestConfig.setAccessControlAllowCredentials("*");
		Perception<HttpRequestConfig> parentDataPerception = new Perception<>(parentHttpRequestConfig);

		HttpRequestConfig childHttpRequestConfig = new HttpRequestConfig();
		childHttpRequestConfig.setRequestTimeout(1000);
		childHttpRequestConfig.setFollowRedirect(true);
		Perception<HttpRequestConfig> childNataPerception = new Perception<>(parentDataPerception,
				childHttpRequestConfig);

		HttpRequestConfig lastHttpRequestConfig = new HttpRequestConfig();
		lastHttpRequestConfig.setRequestTimeout(1300);
		lastHttpRequestConfig.setFollowRedirect(true);
		lastHttpRequestConfig.setAccessControlAllowHeaders("setAccessControlAllowHeaders");
		Perception<HttpRequestConfig> lastNodeDataPerception = new Perception<>(childNataPerception,
				lastHttpRequestConfig);

		parentHttpRequestConfig.setAccessControlAllowCredentials("***");

		parentDataPerception.setPereptionObject(parentHttpRequestConfig);

		HttpRequestConfig newLastHttpRequestConfig = lastNodeDataPerception.pereptionObject();
		Assert.assertEquals(newLastHttpRequestConfig.getAccessControlAllowCredentials(),
				parentHttpRequestConfig.getAccessControlAllowCredentials());
	}
}
