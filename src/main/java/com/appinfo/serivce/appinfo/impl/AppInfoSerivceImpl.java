package com.appinfo.serivce.appinfo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appinfo.mapper.appinfo.AppInfoMapper;
import com.appinfo.mapper.appversion.AppVersionMapper;
import com.appinfo.pojo.AppInfo;
import com.appinfo.serivce.appinfo.AppInfoSerivce;
@Service
@Transactional
public class AppInfoSerivceImpl implements AppInfoSerivce {
	@Resource
	private AppInfoMapper appInfoMapper=null;
	@Resource
	private AppVersionMapper appVersionMapper=null;
	@Override
	@Transactional(readOnly=true)
	public List<AppInfo> getByDevUser(Integer pageIndex, Integer pageSize,
			String softwareName, Integer status, Integer flatformId,
			Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer devUserId) {
		// TODO Auto-generated method stub
		return appInfoMapper.getByDevUser(pageIndex, pageSize, softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, devUserId);
	}
	@Override
	@Transactional(readOnly=true)
	public int getDevUserCount(String softwareName, Integer status,
			Integer flatformId, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer devUserId) {
		// TODO Auto-generated method stub
		return appInfoMapper.getDevUserCount(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, devUserId);
	}
	@Override
	@Transactional(readOnly=false)
	public int checkAPKName(String apkName) {
		// TODO Auto-generated method stub
		return appInfoMapper.checkAPKName(apkName);
	}
	@Override
	public int saveAddAppInfo(AppInfo appInfo) {
		// TODO Auto-generated method stub
		return appInfoMapper.saveAddAppInfo(appInfo);
	}
	@Override
@Transactional(readOnly=true)
	public AppInfo getById(Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.getById(id);
	}
	@Override
	public int updataLogPic(Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.updataLogPic(id);
	}
	@Override
	public int updateById(AppInfo appInfo) {
		// TODO Auto-generated method stub
		return appInfoMapper.updateById(appInfo);
	}
	@Override
	public int updateStatusById(AppInfo appInfo) {
		int count=appInfoMapper.updateById(appInfo);
		if(count>0){
			count=count+appInfoMapper.updateStatusById(appInfo.getId());
		}
		return count;
	}
	@Override
	@Transactional(readOnly=true)
	public AppInfo getAppInfoById(Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfoById(id);
	}
	@Override
	public boolean updateSatus(Integer id) {
		AppInfo appInfo=this.getAppInfoById(id);
		boolean isbig;
		if(appInfo.getStatus()==4){
			isbig=appInfoMapper.updateSatus(5, id);
		}else{
			isbig=appInfoMapper.updateSatus(4, id);
		}
		return isbig;
	}
	@Override
	public int delById(Integer id) {
		AppInfo appInfo=this.getById(id);
		int count=0;
		if(appInfo==null){
			count=-1;
		}else{
			count=appVersionMapper.delByAppId(id);
			if(count>0){
				if(appInfoMapper.delById(id)==0){
					count=0;
				}
			}
		}
		return count;
	}
	@Override
	public boolean updateSatus(AppInfo appInfo) {
		// TODO Auto-generated method stub
		return appInfoMapper.updateSatus(appInfo.getStatus(), appInfo.getId());
	}

}
