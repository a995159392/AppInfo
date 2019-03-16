package com.appinfo.serivce.backenduser;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.BackendUser;

public interface BackendUserSerivce {
	public BackendUser doLogin(String userCode,String userPassword);
}
