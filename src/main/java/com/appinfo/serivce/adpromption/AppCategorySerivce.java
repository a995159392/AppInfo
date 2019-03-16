package com.appinfo.serivce.adpromption;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.AppCategory;
import com.appinfo.pojo.DataDictionary;

public interface AppCategorySerivce {
	public List<AppCategory> getCategory(Integer parentId);
}
