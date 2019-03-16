package com.appinfo.controller.backenduser;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.appinfo.pojo.AppCategory;
import com.appinfo.pojo.AppInfo;
import com.appinfo.pojo.AppVersion;
import com.appinfo.pojo.BackendUser;
import com.appinfo.pojo.DataDictionary;
import com.appinfo.pojo.DevUser;
import com.appinfo.serivce.adpromption.AppCategorySerivce;
import com.appinfo.serivce.appinfo.AppInfoSerivce;
import com.appinfo.serivce.appvesion.AppVesionSerivce;
import com.appinfo.serivce.backenduser.BackendUserSerivce;
import com.appinfo.serivce.datadictionary.DataDictionarySerivce;
import com.appinfo.util.Constants;
import com.appinfo.util.Page;

@Controller
@RequestMapping("/backendUser")
public class BackendUserController {
	@Resource
	private BackendUserSerivce backendUserSerivce=null;
	@Resource
	private DataDictionarySerivce dictionarySerivce=null;
	@Resource
	private AppCategorySerivce appCategorySerivce=null;
	@Resource
	private AppInfoSerivce appInfoSerivce=null;
	@Resource
	private AppVesionSerivce appVesionSerivce=null;
	@RequestMapping("/dologin")
	public String doLogin(@RequestParam("userCode")String userCode,
						  @RequestParam("userPassword")String userPassword,
						  HttpSession session){
		BackendUser backendUser=backendUserSerivce.doLogin(userCode, userPassword);
		if(backendUser!=null){
			session.setAttribute(Constants.USER_SESSION, backendUser);
			return "/backend/main";
		}
		
		return "backendlogin";
	}
	@RequestMapping("/appInfoList")
	public String getByDevUser(
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex,
			@RequestParam(value = "queryFlatformId", required = false) Integer queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) Integer queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) Integer queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) Integer queryCategoryLevel3,
			HttpServletRequest request) {
		Page page = new Page();
		if (pageIndex == null || pageIndex == 0) {
			pageIndex = 1;
		}
		page.setPageSize(5);
		page.setPageIndex(pageIndex);
		
		// 查询记录数
		int count = appInfoSerivce.getDevUserCount(querySoftwareName,
				1, queryFlatformId, queryCategoryLevel1,
				queryCategoryLevel2, queryCategoryLevel3, null);
		page.setTotalCount(count);
		// 查询App列表信息
		List<AppInfo> list = appInfoSerivce.getByDevUser(
				(pageIndex - 1) * page.getPageSize(),
				page.getPageSize(),
				querySoftwareName,
				1,
				queryFlatformId,
				queryCategoryLevel1,
				queryCategoryLevel2,
				queryCategoryLevel3,
				null);
		// 获取状态信息
		List<DataDictionary> appStaus = dictionarySerivce
				.getStatus("APP_STATUS");
		// 获取平台信息
		List<DataDictionary> appFlatfrom = dictionarySerivce
				.getStatus("APP_FLATFORM");
		// 获取一级菜单
		List<AppCategory> oneMenu = appCategorySerivce.getCategory(null);
		request.setAttribute("appStaus", appStaus);
		request.setAttribute("appFlatfrom", appFlatfrom);
		request.setAttribute("oneMenu", oneMenu);
		request.setAttribute("page", page);
		request.setAttribute("appInfoList", list);

		request.setAttribute("querySoftwareName", querySoftwareName);
		
		request.setAttribute("queryFlatformId", queryFlatformId);
		request.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
		request.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
		request.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
		return "backend/applist";
	}
	@RequestMapping("/categorylevellist.json")
	@ResponseBody
	public Object getCategorylevellist(@RequestParam("pid") Integer pid) {
		List<AppCategory> Menu = appCategorySerivce.getCategory(pid);
		return JSONArray.toJSON(Menu);
	}
	@RequestMapping("/check")
	public String check(@RequestParam("aid")Integer aid,
						@RequestParam("vid")Integer vid,
						HttpServletRequest request){
		AppVersion appVersion=appVesionSerivce.getVesionById(vid);
		AppInfo appInfo=appInfoSerivce.getAppInfoById(aid);
		request.setAttribute("appVersion", appVersion);
		request.setAttribute("appInfo", appInfo);
		return "backend/appcheck";
	}
	@RequestMapping("/checksave")
	public String checksave(AppInfo appInfo){
		boolean count=appInfoSerivce.updateSatus(appInfo);
		if(count){
			return "redirect:/backendUser/appInfoList";
		}
		return null;
	}
}
