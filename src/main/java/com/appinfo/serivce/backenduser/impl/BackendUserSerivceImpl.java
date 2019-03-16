package com.appinfo.serivce.backenduser.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appinfo.mapper.backenduser.BackendUserMapper;
import com.appinfo.pojo.BackendUser;
import com.appinfo.serivce.backenduser.BackendUserSerivce;
@Service
public class BackendUserSerivceImpl implements BackendUserSerivce {
	@Resource
	private BackendUserMapper backendUserMapper=null;
	@Override
	public BackendUser doLogin(String userCode, String userPassword) {
		BackendUser backendUser=backendUserMapper.doLogin(userCode);
		if(backendUser!=null){
			if(backendUser.getUserPassword().equals(userPassword)){
				return backendUser;
			}
		}
		return null;
	}

}
