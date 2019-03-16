package com.appinfo.serivce.devuser.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appinfo.mapper.devuser.DevUserMapper;
import com.appinfo.pojo.DevUser;
import com.appinfo.serivce.devuser.DevUserSerivce;
@Service
@Transactional
public class DevUserSerivceImpl implements DevUserSerivce {
	@Resource
	private DevUserMapper devUserMapper=null;
	@Override
	@Transactional(readOnly=true)
	public DevUser login(String devCode,String pwd) {
		// TODO Auto-generated method stub
		DevUser devUser=devUserMapper.login(devCode);
		if(devUser!=null){
			if(devUser.getDevPassword().equals(pwd))
				return devUser;
		}
		return null;
	}

}
