package com.appinfo.controller.devuser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.appinfo.pojo.AppCategory;
import com.appinfo.pojo.AppInfo;
import com.appinfo.pojo.AppVersion;
import com.appinfo.pojo.DataDictionary;
import com.appinfo.pojo.DevUser;
import com.appinfo.serivce.adpromption.AppCategorySerivce;
import com.appinfo.serivce.appinfo.AppInfoSerivce;
import com.appinfo.serivce.appvesion.AppVesionSerivce;
import com.appinfo.serivce.datadictionary.DataDictionarySerivce;
import com.appinfo.serivce.devuser.DevUserSerivce;
import com.appinfo.util.Constants;
import com.appinfo.util.Page;

@Controller
@RequestMapping("/devuser")
public class DevUserController {
	@Resource
	private DevUserSerivce devUserSerivce = null;
	@Resource
	private DataDictionarySerivce dictionarySerivce = null;
	@Resource
	private AppCategorySerivce appCategorySerivce = null;
	@Resource
	private AppInfoSerivce appInfoSerivce = null;
	@Resource
	private AppVesionSerivce appVesionSerivce=null;
	// 开发者登录
	@RequestMapping("/login")
	public String login(@RequestParam("devCode") String userCode,
			@RequestParam("devPassword") String pwd, HttpSession session,
			Model model) {
		DevUser devUser = devUserSerivce.login(userCode, pwd);
		if (devUser == null) {
			model.addAttribute("error", "账号密码错误!");
			return "devlogin";
		} else {
			session.setAttribute(Constants.DEV_USER_SESSION, devUser);
			return "/developer/main";
		}
	}

	// App维护
	@RequestMapping("/appInfoList1")
	public String goAppInfoList(Model model) {
		List<DataDictionary> appStaus = dictionarySerivce
				.getStatus("APP_STATUS");
		List<DataDictionary> appFlatfrom = dictionarySerivce
				.getStatus("APP_FLATFORM");
		List<AppCategory> oneMenu = appCategorySerivce.getCategory(null);
		model.addAttribute("appStaus", appStaus);
		model.addAttribute("appFlatfrom", appFlatfrom);
		model.addAttribute("oneMenu", oneMenu);
		return "developer/appinfolist";
	}
	
	@RequestMapping("/appInfoList")
	public String getByDevUser(
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex,
			@RequestParam(value = "queryStatus", required = false) Integer queryStatus,
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
		DevUser user = ((DevUser) request.getSession().getAttribute(
				Constants.DEV_USER_SESSION));
		// 查询记录数
		int count = appInfoSerivce.getDevUserCount(querySoftwareName,
				queryStatus, queryFlatformId, queryCategoryLevel1,
				queryCategoryLevel2, queryCategoryLevel3, user.getId());
		page.setTotalCount(count);
		// 查询App列表信息
		List<AppInfo> list = appInfoSerivce.getByDevUser(
				(pageIndex - 1) * page.getPageSize(),
				page.getPageSize(),
				querySoftwareName,
				queryStatus,
				queryFlatformId,
				queryCategoryLevel1,
				queryCategoryLevel2,
				queryCategoryLevel3,
				((DevUser) request.getSession().getAttribute(
						Constants.DEV_USER_SESSION)).getId());
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
		request.setAttribute("queryStatus", queryStatus);
		request.setAttribute("queryFlatformId", queryFlatformId);
		request.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
		request.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
		request.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
		return "developer/appinfolist";
	}
	//app信息新增
	@RequestMapping("/appinfoaddsave")
	public String appInfoAddSave(
			AppInfo appInfo,
			@RequestParam(value = "a_logoPicPath", required = false) MultipartFile multipartFile,
			HttpServletRequest request) {
		String fileName = null;
		if (!multipartFile.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics/images");
			String logoPic = multipartFile.getOriginalFilename();
			String suffix = FilenameUtils.getExtension(logoPic);
			if (multipartFile.getSize() > 50000) {
				request.setAttribute("fileUploadError", "文件大小不能超过50KB");
				return "/developer/appinfoadd";
			} else if (suffix.equals("jpg") || suffix.equals("png")
					|| suffix.equals("jepg")) {
				fileName = path + "/" + UUID.randomUUID() + "." + suffix;
				File file = new File(fileName);
				file.mkdir();
				try {
					multipartFile.transferTo(file);
					appInfo.setLogoPicPath(fileName);
					String[] fileNames = fileName.split("\\\\");
					String fileName2 = "";
					for (int i = 0; i < fileNames.length; i++) {
						if (i > 4) {
							fileName2 = fileName2 + "/" + fileNames[i];
						}
					}
					appInfo.setLogoPicPath(fileName2);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "文件上传失败！");
					return "/developer/appinfoadd";
				}

			} else {
				request.setAttribute("fileUploadError", "文件格式错误！");
				return "/developer/appinfoadd";
			}
		}

		appInfo.setCreatedBy(((DevUser) request.getSession().getAttribute(
				Constants.DEV_USER_SESSION)).getId());
		appInfo.setDevId(((DevUser) request.getSession().getAttribute(
				Constants.DEV_USER_SESSION)).getId());
		
		appInfo.setCreationDate(new Date());

		int row = appInfoSerivce.saveAddAppInfo(appInfo);
		if (row > 0) {
			return "redirect:/devuser/appInfoList";
		} else {
			request.setAttribute("fileUploadError", "新增失败");
			return "/developer/appinfoadd";
		}

	}

	@RequestMapping("/getByAppInfoId")
	public String getByAppInfoId(@RequestParam("id") Integer appInfoId,
			HttpServletRequest request) {
		AppInfo appInfo = appInfoSerivce.getById(appInfoId);
		request.setAttribute("appInfo", appInfo);
		return "/developer/appinfomodify";
	}

	/**
	 * @param appInfo
	 * @param multipartFile
	 * @param request
	 * @param session
	 * @return
	 * 修改App信息
	 */
	@RequestMapping("/updateById")
	public String updateById(
			AppInfo appInfo,
			@RequestParam(value = "attach", required = false) MultipartFile multipartFile,
			HttpServletRequest request, HttpSession session) {
		String fileName = null;
		String path = request.getSession().getServletContext()
				.getRealPath("/statics/images");
		if (!multipartFile.isEmpty()) {
			fileName = multipartFile.getOriginalFilename();
			String suffix = FilenameUtils.getExtension(fileName);
			if (multipartFile.getSize() > 50000) {
				request.setAttribute("fileUploadError", "文件超大！");
				return "developer/appinfomodify";
			} else if (suffix.equals("jpg") || suffix.equals("png")
					|| suffix.equals("jpeg")) {
				fileName = path + "\\\\" + UUID.randomUUID() + "." + suffix;
				File file = new File(fileName);
				file.mkdir();
				try {
					multipartFile.transferTo(file);
					appInfo.setLogoLocPath(fileName);
					String[] fileNames = fileName.split("\\\\");
					String fileName2 = "";
					for (int i = 0; i < fileNames.length; i++) {
						if (i > 4) {
							fileName2 = fileName2 + "/" + fileNames[i];
						}
					}
					appInfo.setLogoPicPath(fileName2);
				} catch (Exception e) {
					request.setAttribute("fileUploadError", "文件上传失败！");
					return "developer/appinfomodify";
				}
			} else {
				request.setAttribute("fileUploadError", "文件格式不对！");
				return "developer/appinfomodify";
			}
		}
		appInfo.setModifyBy(((DevUser) session
				.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appInfo.setModifyDate(new Date());
		appInfo.setDevId(((DevUser) session
				.getAttribute(Constants.DEV_USER_SESSION)).getId());
		int count = appInfoSerivce.updateById(appInfo);
		if (count > 0) {
			return "redirect:/devuser/appInfoList";
		} else {
			request.setAttribute("fileUploadError", "修改失败！");
			return "developer/appinfomodify";
		}
	}
	/**
	 * @param appInfo
	 * @param multipartFile
	 * @param request
	 * @param session
	 * @return
	 * 修改状态
	 */
	@RequestMapping("/updateStatusById")
	public String updateStatusById(AppInfo appInfo,
			@RequestParam(value = "attach", required = false) MultipartFile multipartFile,
			HttpServletRequest request, HttpSession session){
		String fileName = null;
		String path = request.getSession().getServletContext()
				.getRealPath("/statics/images");
		if (!multipartFile.isEmpty()) {
			fileName = multipartFile.getOriginalFilename();
			String suffix = FilenameUtils.getExtension(fileName);
			if (multipartFile.getSize() > 50000) {
				request.setAttribute("fileUploadError", "文件超大！");
				return "developer/appinfomodify";
			} else if (suffix.equals("jpg") || suffix.equals("png")
					|| suffix.equals("jpeg")) {
				fileName = path + "\\\\" + UUID.randomUUID() + "." + suffix;
				File file = new File(fileName);
				file.mkdir();
				try {
					multipartFile.transferTo(file);
					appInfo.setLogoLocPath(fileName);
					String[] fileNames = fileName.split("\\\\");
					String fileName2 = "";
					for (int i = 0; i < fileNames.length; i++) {
						if (i > 4) {
							fileName2 = fileName2 + "/" + fileNames[i];
						}
					}
					appInfo.setLogoPicPath(fileName2);
				} catch (Exception e) {
					request.setAttribute("fileUploadError", "文件上传失败！");
					return "developer/appinfomodify";
				}
			} else {
				request.setAttribute("fileUploadError", "文件格式不对！");
				return "developer/appinfomodify";
			}
		}
		appInfo.setModifyBy(((DevUser) session
				.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appInfo.setModifyDate(new Date());
		appInfo.setDevId(((DevUser) session
				.getAttribute(Constants.DEV_USER_SESSION)).getId());
		int count = appInfoSerivce.updateStatusById(appInfo);
		if (count > 1) {
			return "redirect:/devuser/appInfoList";
		} else {
			request.setAttribute("fileUploadError", "修改失败！");
			return "developer/appinfomodify";
		}
	
	}
	/**
	 * @param appVersion
	 * @param multipartFile
	 * @param request
	 * @return
	 * APP版本修改
	 */
	@RequestMapping("/appversionmodifysave")
	public String appVersionModifySave(AppVersion appVersion,
@RequestParam(value="attach",required=false)MultipartFile multipartFile,
										HttpServletRequest request){
		if(!multipartFile.isEmpty()){
			String path=request.getSession().getServletContext().getRealPath("/statics/uploadfiles");
			String fileName=multipartFile.getOriginalFilename();
			String sufix=FilenameUtils.getExtension(fileName);
			if(sufix.equals("apk")){
				fileName=path+"/"+UUID.randomUUID()+"."+sufix;
				File file =new File(fileName);
				file.mkdir();
				try {
					multipartFile.transferTo(file);
				} catch (Exception e) {
					request.setAttribute("fileUploadError", "文件上传失败");
					return "developer/appversionmodify";
				}
			}else{
				request.setAttribute("fileUploadError", "文件格式不正确");
				return "developer/appversionmodify";
			}
			appVersion.setApkLocPath(fileName);
			String[] fileNames=fileName.split("\\\\");
			String fileName2 = "";
			for (int i = 0; i < fileNames.length; i++) {
				if (i > 4) {
					fileName2 = fileName2 + "/" + fileNames[i];
				}
			}
			appVersion.setApkFileName(fileName.substring(fileName.lastIndexOf("/")));
			appVersion.setDownloadLink(fileName2);
		}
		int count=appVesionSerivce.modify(appVersion);
		if(count>0){
			return "redirect:/devuser/appInfoList";
		}else{
			request.setAttribute("fileUploadError", "修改失败！");
			return "developer/appversionmodify";
		}
	}
	/**
	 * @param vid
	 * @param aid
	 * @param request
	 * @return
	 * 获取需要修改的版本信息
	 */
	@RequestMapping("/appversionmodify")
	public String appVersionModify(@RequestParam("vid")Integer vid,
									@RequestParam("aid")Integer aid,
									HttpServletRequest request){
		AppVersion appVersion=appVesionSerivce.getVesionById(vid);
		List<AppVersion> list=appVesionSerivce.getVesionByAppId(aid);
		request.setAttribute("appVersionList", list);
		request.setAttribute("appVersion", appVersion);
		return "developer/appversionmodify";
	}
	@RequestMapping("/appversionadd")
	public String appVersionAdd(@RequestParam("id")Integer id,
								HttpServletRequest request){
			List<AppVersion> list=appVesionSerivce.getVesionByAppId(id);
			AppVersion appVersion=new AppVersion();
			appVersion.setAppId(id);
			request.setAttribute("appVersion", appVersion);
			request.setAttribute("appVersionList", list);
			return "/developer/appversionadd";
	}
	@RequestMapping("/addversionsave")
	public String addVersionSave(AppVersion appVersion,
				@RequestParam(value="a_downloadLink")MultipartFile multipartFile,
								HttpServletRequest request){
		if(!multipartFile.isEmpty()){
			String path=request.getSession().getServletContext().getRealPath("/statics/uploadfiles");
			String fileName=multipartFile.getOriginalFilename();
			String sufix=FilenameUtils.getExtension(fileName);
			if(sufix.equals("apk")){
				fileName=path+"/"+UUID.randomUUID()+"."+sufix;
				File file =new File(fileName);
				file.mkdir();
				try {
					multipartFile.transferTo(file);
				} catch (Exception e) {
					request.setAttribute("fileUploadError", "文件上传失败");
					return "developer/appversionadd";
				}
			}else{
				request.setAttribute("fileUploadError", "文件格式不正确");
				return "developer/appversionadd";
			}
			appVersion.setApkLocPath(fileName);
			String[] fileNames=fileName.split("\\\\");
			String fileName2 = "";
			for (int i = 0; i < fileNames.length; i++) {
				if (i > 4) {
					fileName2 = fileName2 + "/" + fileNames[i];
				}
			}
			appVersion.setApkFileName(fileName.substring(fileName.lastIndexOf("/")));
			appVersion.setDownloadLink(fileName2);
			
			appVersion.setCreationDate(new Date());
		}
		int count=appVesionSerivce.addVersion(appVersion);
		if(count>1){
			return "redirect:/devuser/appInfoList";
		}else{
			request.setAttribute("fileUploadError", "新增失败！");
			return "developer/appversionadd";
		}
		
	}
	/**
	 * @param pid
	 * @return
	 * 获取分类
	 */
	@RequestMapping("/appview")
	public String getAppInfoById(@RequestParam("id")Integer id,
								HttpServletRequest request){
		AppInfo appInfo=appInfoSerivce.getAppInfoById(id);
		List<AppVersion> list=appVesionSerivce.getVesionByAppId(id);
		request.setAttribute("appVersionList", list);
		request.setAttribute("appInfo", appInfo);
		return "/developer/appinfoview";
	}
	@RequestMapping("/down")  
    public void down(HttpServletRequest request,HttpServletResponse response,
    				@RequestParam("fileName")String fileName
    		) throws Exception{  
		 String filename = fileName;  
        fileName = request.getSession().getServletContext().getRealPath("statics/uploadfiles")+"/"+fileName;  
        
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));  
          
        filename = URLEncoder.encode(filename,"UTF-8");  
        
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
            
        response.setContentType("multipart/form-data");   
        
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        int len = 0;  
        while((len = bis.read()) != -1){  
            out.write(len);  
            out.flush();  
        }  
        out.close();  
    }  
	@RequestMapping("/downExecel")
	public void downExecel(HttpServletResponse response) throws Exception{
		response.setHeader("Content-disposition", "attachment; filename=applist.xls");// 设定输出文件头
        response.setContentType("application/msexcel");// 定义输出类型
        WritableWorkbook wrokBook=Workbook.createWorkbook(response.getOutputStream());
        WritableSheet sheet=wrokBook.createSheet("appinfo", 0);
        sheet.addCell(new Label(0,0,"软件名称"));
		sheet.addCell(new Label(1,0,"APK名称"));
		sheet.addCell(new Label(2,0,"软件大小(单位:M)"));
		sheet.addCell(new Label(3,0,"所属平台"));
		sheet.addCell(new Label(4,0,"所属分类(一级分类、二级分类、三级分类)"));
		sheet.addCell(new Label(5,0,"状态"));
		sheet.addCell(new Label(6,0,"下载次数"));
		sheet.addCell(new Label(7,0,"最新版本号"));
		List<AppInfo> list = appInfoSerivce
				.getByDevUser(1, 1000, null, null, null, null, null, null, null);
		for (int i = 0; i < list.size(); i++) {
			AppInfo appInfo = list.get(i);
			sheet.addCell(new Label(0, i+1,  appInfo.getSoftwareName()));
			sheet.addCell(new Label(1, i+1,  appInfo.getAPKName()));
			sheet.addCell(new Label(2, i+1,  ""+appInfo.getSoftwareSize()));
			sheet.addCell(new Label(3, i+1,  appInfo.getFlatformName()));
			sheet.addCell(new Label(4, i+1,  appInfo.getCategoryLevel1Name()));
			sheet.addCell(new Label(5, i+1,  appInfo.getStatusName()));
			sheet.addCell(new Label(6, i+1,  ""+appInfo.getDownloads()));
			sheet.addCell(new Label(7, i+1,  appInfo.getVersionNo()));
		}
		wrokBook.write(); // 写入文件
		wrokBook.close();
	}
	@RequestMapping("/categorylevellist.json")
	@ResponseBody
	public Object getCategorylevellist(@RequestParam("pid") Integer pid) {
		List<AppCategory> Menu = appCategorySerivce.getCategory(pid);
		return JSONArray.toJSON(Menu);
	}
	/**
	 * @param id
	 * @return
	 * 删除版本文件
	 */
	@RequestMapping("/delfile")
	@ResponseBody
	public Object delFileVesion(@RequestParam("id")Integer id){
		Map<String, String> map=new HashMap<String, String>();
		AppVersion appVersion=appVesionSerivce.getVesionById(id);
		File file=new File(appVersion.getApkLocPath());
		file.delete();
		int count=appVesionSerivce.delFile(id);
		if(count>0){
			map.put("result", "success");
		}else{
			map.put("result", "failed");
		}
		return JSONArray.toJSON(map);
	}
	/**
	 * @param tcode
	 * @return
	 * 获取平台ID
	 */
	@RequestMapping("/datadictionarylist")
	@ResponseBody
	public Object getFormId(@RequestParam("tcode") String tcode) {
		List<DataDictionary> appFlatfrom = dictionarySerivce
				.getStatus("APP_FLATFORM");
		return JSONArray.toJSON(appFlatfrom);
	}
	
	/**
	 * @param apkName
	 * @return
	 * 重名验证
	 */
	@RequestMapping("checkAPKName")
	@ResponseBody
	public Object checkAPKName(@RequestParam("APKName") String apkName) {
		Map<String, String> map = new HashMap<String, String>();
		if (apkName == null || apkName.equals("")) {
			map.put("APKName", "empty");
		} else {
			int count = appInfoSerivce.checkAPKName(apkName);
			if (count > 0) {
				map.put("APKName", "exist");
			} else {
				map.put("APKName", "noexist");
			}
		}
		return JSONArray.toJSON(map);
	}

	/**
	 * @param id
	 * @return
	 * 删除文件
	 */
	@RequestMapping("delfile.json")
	@ResponseBody
	public Object delFile(@RequestParam("id") Integer id) {
		AppInfo appInfo = appInfoSerivce.getById(id);
		File file = new File(appInfo.getLogoLocPath());
		file.delete();
		Map<String, String> map = new HashMap<String, String>();
		int count = appInfoSerivce.updataLogPic(id);
		if (count > 0) {
			map.put("result", "success");
		} else {
			map.put("result", "failed");
		}
		return JSONArray.toJSON(map);
	}
	@RequestMapping(value="sale.json",method=RequestMethod.POST)
	@ResponseBody
	public Object sale(@RequestParam("id")Integer id,HttpServletRequest request){
		Map<String, String> map=new HashMap<String, String>();
		boolean isbig=appInfoSerivce.updateSatus(id);
		if(isbig){
			map.put("errorCode", "0");
			map.put("resultMsg", "success");
		}else{
			map.put("errorCode", "0");
			map.put("resultMsg", "failed");
		}
		return JSONArray.toJSON(map);
	}
	@RequestMapping("/delapp.json")
	@ResponseBody
	public String delApp(@RequestParam("id")Integer id){
		int count=appInfoSerivce.delById(id);
		Map<String, String> map=new HashMap<String, String>();
		if(count>0){
			map.put("delResult", "true");
		}else if(count<0){
			map.put("delResult", "notexist");
		}else{
			map.put("delResult", "false");
		}
		return JSONArray.toJSONString(map);
	}
}
