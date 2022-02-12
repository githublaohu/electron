package com.lamp.china.spring.mvc.example;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lamp.electron.base.common.service.authentication.AuthResponseEnum;
import com.lamp.electron.base.common.service.authentication.AuthenticationRequestData;
import com.lamp.electron.base.common.service.authentication.AuthenticationResponseData;
import com.lamp.electron.base.common.service.authentication.AuthenticationService;
import com.lamp.electron.example.service.example.UserInfo;

@RestController
@RequestMapping("/auth")
public class ElectronAuthenticationService implements AuthenticationService{

    private static Set<String> uri = new HashSet<>();
    
    static {
        uri.add("/electron/example/example/queryExampleList");
        uri.add("/electron/example/example/addExample");
    }
	
	@Override
	@PostMapping("userAuth")
	public AuthenticationResponseData userAuth(AuthenticationRequestData authRequestData) {
		return createData(authRequestData);
	}

	@Override
	@PostMapping("jurisdictionAuth")
	public AuthenticationResponseData jurisdictionAuth(AuthenticationRequestData authRequestData) {
		return null;
	}
	
	public static AuthenticationResponseData createData(AuthenticationRequestData authRequestData) {
		AuthenticationResponseData authResponseData = new AuthenticationResponseData();
		Long token;
        try {
            token = Long.valueOf(authRequestData.getToken());
        }catch(Exception e) {
            authResponseData.setStatus(AuthResponseEnum.AUTH_RESULT_USER_FAIL);
            return authResponseData;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(token);
        userInfo.setName(authRequestData.getToken());
        String userInfoData = JSON.toJSONString(userInfo);
        if(token < 100000) {
            authResponseData.setStatus(AuthResponseEnum.AUTH_RESULT_SUCCESS);
            authResponseData.setUserData(userInfoData);
            authResponseData.setUserkey( token + "");
        }else if(token > 100000 && token < 200000) {
            authResponseData.setStatus(AuthResponseEnum.AUTH_RESULT_USER_FAIL);
        }else if(token > 200000 ){
            for(String uri : uri) {
                if(authRequestData.getUrl().startsWith(uri)) {
                    authResponseData.setStatus(AuthResponseEnum.AUTH_RESULT_SUCCESS);
                    authResponseData.setUserData(userInfoData);
                    authResponseData.setUserkey(token+"");
                    return authResponseData;
                }
            }
            
            authResponseData.setStatus(AuthResponseEnum.AUTH_RESULT_JURISDICTION_FAIL);
            
        }
       
        return authResponseData;
    }

}
