package com.appinfo.mapper.backenduser;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.BackendUser;

public interface BackendUserMapper {
	public BackendUser doLogin(@Param("userCode") String userCode);
}
