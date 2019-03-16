package com.appinfo.serivce.appvesion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.AppVersion;

public interface AppVesionSerivce {
	public List<AppVersion> getVesionByAppId(Integer aid);
	public AppVersion getVesionById(@Param("id")Integer id);
	public int modify(AppVersion appVersion);
	public int delFile(@Param("id")Integer id);
	public int addVersion(AppVersion appVersion);
}
