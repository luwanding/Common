package com.chyjr.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")  
public class TestController {
	
	@RequestMapping(value="/userName", method=RequestMethod.GET)
	public String getUserName(){
//		CacheInitialization
		return "chyjr.com";
	}

}
