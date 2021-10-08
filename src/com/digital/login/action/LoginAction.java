package com.digital.login.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digital.login.entity.Login;
import com.digital.login.service.LoginService;
import com.digital.util.PageHelper.Page;



@Controller
public class LoginAction {
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	@Resource(name = "loginservice")
	LoginService loginservice;

	public LoginService getLoginService() {
		return loginservice;
	}

	public void setLoginService(LoginService loginservice) {
		this.loginservice = loginservice;
	}

	@RequestMapping("/login/action/LoginActionFind")
	public @ResponseBody String LoginActionFind(@ModelAttribute("login") Login login) {
		logger.info("打印日志");
		logger.error("打印日志2");
		logger.debug("打印日志3");
	    login.getUsername();
	    login.getPassword();
		int num = loginservice.loginServiceFind(login);
		if (num>0){
			String result="true";
		    return result;
		}
		else{
			String result="false";
		    return result;
	}
	}
	
	@RequestMapping("/login/action/LoginActionFindAll")
	public @ResponseBody Map<String, Object> LoginActionFindAll(@RequestParam("pageNum")
			String pageNum,@RequestParam("pageSize") String pageSize){
		Page<Login> page=loginservice.loginServiceFindAll(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		for(int i=0;i<page.getResult().size();i++){
//			Login login=(Login) page.getResult().get(i);
//		}
//		System.out.println(login.getName());
//		System.out.println(login.getPassword());
//		
//		System.out.println("EndNum"+page.getEndRow());
//		System.out.println("getPageNum"+page.getPageNum());
//		System.out.println("getPageSize"+page.getPageSize());
//		System.out.println("totalPage"+page.getPages());
//		System.out.println("StartNum"+page.getStartRow());
//		System.out.println("TotalNum"+page.getTotal());
		Map<String, Object> modelMap =new HashMap<String, Object>(3);
//		modelMap.put("totalNum", page.getTotal());
		modelMap.put("totalPage", page.getPages());
		modelMap.put("pagelist", page.getResult());
		return modelMap;
	}
	
	@RequestMapping("/login/action/LoginRedis")
	public @ResponseBody Map<String, Object> LoginRedis(@RequestParam("pageNum")
			String pageNum,@RequestParam("pageSize") String pageSize){
		Login login=loginservice.loginRedis(Integer.parseInt(pageNum), Integer.parseInt(pageSize));

		Map<String, Object> modelMap =new HashMap<String, Object>(3);
		modelMap.put("loginredis", login);
		return modelMap;
	}

}
