package com.appinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jump")
public class JumpController {

	@RequestMapping("/jumpDevUser")
	public String goDevUser() {
		return "devlogin";
	}

	@RequestMapping("/jumpBackend")
	public String goBackend() {
		return "backendlogin";
	}
	
	@RequestMapping("/junmpListAdd")
	public String junmpListAdd(){
		return "developer/appinfoadd";
	}
}
