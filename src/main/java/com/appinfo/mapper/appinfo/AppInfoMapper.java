package com.appinfo.mapper.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.AppInfo;

public interface AppInfoMapper {
	/**
	 * @param pageIndex
	 * @param pageSize
	 * @param apkName
	 * @param status
	 * @param oneMenue
	 * @param twoMenue
	 * @param treeMenue
	 * @param devUserId
	 * 查询用户App列表
	 * @return
	 */
	public List<AppInfo> getByDevUser(@Param("pageIndex") Integer pageIndex,
			@Param("pageSize") Integer pageSize,
			@Param("softwareName") String softwareName, 
			@Param("status") Integer status,
			@Param("flatformId") Integer flatformId,
			@Param("categoryLevel1") Integer categoryLevel1,
			@Param("categoryLevel2") Integer categoryLevel2,
			@Param("categoryLevel3") Integer categoryLevel3,
			@Param("devUserId")Integer devUserId);
	/**
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param devUserId
	 * @return
	 */
	public int getDevUserCount(@Param("softwareName") String softwareName, 
			@Param("status") Integer status,
			@Param("flatformId") Integer flatformId,
			@Param("categoryLevel1") Integer categoryLevel1,
			@Param("categoryLevel2") Integer categoryLevel2,
			@Param("categoryLevel3") Integer categoryLevel3,
			@Param("devUserId")Integer devUserId);
	/**
	 * @param apkName
	 * @return
	 */
	public int checkAPKName(@Param("APKname")String APKname);
	/**
	 * @param appInfo
	 * @return
	 */
	public int saveAddAppInfo(AppInfo appInfo);
	/**
	 * @param id
	 * @return
	 */
	public AppInfo getById(@Param("id")Integer id);
	/**
	 * @param id
	 * @return
	 */
	public int updataLogPic(@Param("id")Integer id);
	/**
	 * @param id
	 * @return
	 */
	public int updateById(AppInfo appInfo);
	/**
	 * @param id
	 * @return
	 */
	public int updateStatusById(@Param("id")Integer id);
	/**
	 * @param id
	 * @return
	 */
	public AppInfo getAppInfoById(@Param("id")Integer id);
	/**
	 * @param id
	 * @param versionId
	 * @return
	 */
	public int updateVersionId(@Param("id")Integer id,@Param("versionId")Integer versionId);
	/**
	 * @param status
	 * @param id
	 * @return
	 */
	public boolean updateSatus(@Param("status")Integer status,@Param("id")Integer id);
	
	/**
	 * @param id
	 * @return
	 */
	public int delById(@Param("id")Integer id);
}
