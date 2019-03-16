package com.appinfo.mapper.appcategory;

import java.util.List;
import com.appinfo.pojo.AppCategory;
import org.apache.ibatis.annotations.Param;
public interface AppCategoryMapper {
	public List<AppCategory> getCategory(@Param("parentId")Integer parentId);
}
