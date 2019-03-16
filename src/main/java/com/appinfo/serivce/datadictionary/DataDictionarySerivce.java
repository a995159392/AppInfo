package com.appinfo.serivce.datadictionary;

import java.util.List;

import com.appinfo.pojo.DataDictionary;

public interface DataDictionarySerivce {
	public List<DataDictionary> getStatus(String typeCode);
}
