package com.appinfo.serivce.appvesion.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appinfo.mapper.appinfo.AppInfoMapper;
import com.appinfo.mapper.appversion.AppVersionMapper;
import com.appinfo.pojo.AppVersion;
import com.appinfo.serivce.appvesion.AppVesionSerivce;
@Service
@Transactional
public class AppVesionSerivceImpl implements AppVesionSerivce {
	@Resource
	private AppVersionMapper appVersionMapper=null;
	@Resource
	private AppInfoMapper appInfoMapper=null;
	@Override
	@Transactional(readOnly=true)
	public List<AppVersion> getVesionByAppId(Integer aid) {
		// TODO Auto-generated method stub
		return appVersionMapper.getVesionByAppId(aid);
	}
	@Override
	@Transactional(readOnly=true)
	public AppVersion getVesionById(Integer id) {
		// TODO Auto-generated method stub
		return appVersionMapper.getVesionById(id);
	}
	@Override
	public int modify(AppVersion appVersion) {
		// TODO Auto-generated method stub
		return appVersionMapper.modify(appVersion);
	}
	@Override
	public int delFile(Integer id) {
		// TODO Auto-generated method stub
		return appVersionMapper.delFile(id);
	}
	@Override
	public int addVersion(AppVersion appVersion) {
		// TODO Auto-generated method stub
		int count=appVersionMapper.addVersion(appVersion);
		if(count>0){
			count=count+appInfoMapper.updateVersionId(appVersion.getAppId(), appVersion.getId());
		}
		return  count;
	}

}
