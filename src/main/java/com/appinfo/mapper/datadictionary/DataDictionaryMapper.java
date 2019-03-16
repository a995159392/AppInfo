package com.appinfo.mapper.datadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.DataDictionary;
public interface DataDictionaryMapper {
	public List<DataDictionary> getStatus(@Param("typeCode")String typeCode);
}
