package com.appinfo.serivce.adpromption.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appinfo.mapper.appcategory.AppCategoryMapper;
import com.appinfo.mapper.datadictionary.DataDictionaryMapper;
import com.appinfo.pojo.AppCategory;
import com.appinfo.pojo.DataDictionary;
import com.appinfo.serivce.adpromption.AppCategorySerivce;
import com.appinfo.serivce.datadictionary.DataDictionarySerivce;
@Service
@Transactional
public class AppCategorySerivceImpl implements AppCategorySerivce {
	@Resource
	private AppCategoryMapper appCategoryMapper=null;

	@Override
	@Transactional(readOnly=true)
	public List<AppCategory> getCategory(Integer parentId) {
		// TODO Auto-generated method stub
		return appCategoryMapper.getCategory(parentId);
	}

	
	

}
