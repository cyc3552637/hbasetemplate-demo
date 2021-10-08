package com.digital.mobile.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digital.mobile.entity.TestEntity;
import com.digital.mobile.service.TestService;
import com.digital.login.entity.Login;
import com.digital.util.JsonUtil;
import com.digital.util.PageHelper.Page;




@Controller
public class TestAction {
	@Resource(name = "testservice")
	TestService testservice;

	public TestService getTestService() {
		return testservice;
	}

	public void setTestService(TestService testservice) {
		this.testservice = testservice;
	}

	@RequestMapping(value="/mobile/action/testActionFind",produces = "application/json; charset=utf-8")
	public @ResponseBody String  testActionFind(@ModelAttribute("testentity")TestEntity testentity) {
		Page page = testservice.testServiceFind(testentity);
		List<TestEntity> list=page.getResult();
		JsonUtil ju=new JsonUtil();
		Map<String, Object> modelMap =new HashMap<String, Object>(3);
		String json=ju.list2json(list);
//		modelMap.put("jsonlist", json);
		//view.addObject("itemCount", list.size());
		return json;
	}
	@RequestMapping("/mobile/action/testActionAdd")
	public @ResponseBody String testActionAdd(@ModelAttribute("testentity") TestEntity testentity) {

		int num = testservice.testServiceAdd(testentity);
		if (num>0){
			String result="true";
		    return result;
		}
		else{
			String result="false";
		    return result;
	}

	}


}
