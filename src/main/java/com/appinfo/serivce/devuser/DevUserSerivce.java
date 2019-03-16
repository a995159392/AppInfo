package com.appinfo.serivce.devuser;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.DevUser;

public interface DevUserSerivce {
	public DevUser login(String devCode,String pwd);
}
