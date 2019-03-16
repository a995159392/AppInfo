package com.appinfo.mapper.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.AppVersion;

public interface AppVersionMapper {
	/**
	 * @param aid
	 * @return
	 */
	public List<AppVersion> getVesionByAppId(@Param("aid")Integer aid);
	/**
	 * @param id
	 * @return
	 */
	public AppVersion getVesionById(@Param("id")Integer id);
	/**
	 * @param appVersion
	 * @return
	 */
	public int modify(AppVersion appVersion);
	/**
	 * @param id
	 * @return
	 */
	public int delFile(@Param("id")Integer id);
	/**
	 * @param appVersion
	 * @return
	 */
	public int addVersion(AppVersion appVersion);
	/**
	 * @param id
	 * @return
	 */
	public int delByAppId(@Param("id")Integer id);

}
