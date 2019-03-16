package com.appinfo.serivce.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.AppInfo;

public interface AppInfoSerivce {
	public List<AppInfo> getByDevUser(Integer pageIndex,Integer pageSize,String softwareName, Integer status,Integer flatformId,Integer categoryLevel1,Integer categoryLevel2,Integer categoryLevel3,Integer devUserId);
	public int getDevUserCount(String softwareName, Integer status,Integer flatformId,Integer categoryLevel1,Integer categoryLevel2,Integer categoryLevel3,Integer devUserId);
	public int checkAPKName(String apkName);
	public int saveAddAppInfo(AppInfo appInfo);
	public AppInfo getById(Integer id);
	public int updataLogPic(Integer id);
	public int updateById(AppInfo appInfo);
	public int updateStatusById(AppInfo appInfo);
	public AppInfo getAppInfoById(Integer id);
	public boolean updateSatus(Integer id);
	public int delById(@Param("id")Integer id);
	public boolean updateSatus(AppInfo appInfo);
}
