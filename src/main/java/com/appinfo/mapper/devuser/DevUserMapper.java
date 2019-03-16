package com.appinfo.mapper.devuser;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.DevUser;

public interface DevUserMapper {
	public DevUser login(@Param("devCode")String devCode);
}
