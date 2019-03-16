package com.appinfo.serivce.datadictionary.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appinfo.mapper.datadictionary.DataDictionaryMapper;
import com.appinfo.pojo.DataDictionary;
import com.appinfo.serivce.datadictionary.DataDictionarySerivce;
@Service
@Transactional
public class DataDictionarySerivceImpl implements DataDictionarySerivce {
	@Resource
	private  DataDictionaryMapper dataDictionaryMapper=null;
	@Override
	public List<DataDictionary> getStatus(String typeCode) {
		// TODO Auto-generated method stub
		return dataDictionaryMapper.getStatus(typeCode);
	}

}
