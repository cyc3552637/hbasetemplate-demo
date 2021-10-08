package com.digital.hbase.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digital.hbase.dao.HbaseDao;
import com.digital.hbase.entity.HbaseModel;
import com.digital.util.JsonUtil;

@Controller
public class HbaseAction {
	@Resource(name = "hbasedao")
	HbaseDao hbasedao;

	public HbaseDao getHbaseimpl() {
		return hbasedao;
	}

	public void setHbaseimpl(HbaseDao hbasedao) {
		this.hbasedao = hbasedao;
	}

	@RequestMapping(value="/hbase/action/doHbase",produces = "application/json;charset=utf-8")		 
	public @ResponseBody String doHbase() {
		//PrefixFilter
		System.setProperty("hadoop.home.dir", "D:\\Program Files\\hadoop-2.7.2");  
		for(int i=0;i<=7;i++){
			hbasedao.setKey("linlin"+i);
			hbasedao.setFamilyName("info"); 
			hbasedao.setQualifier("service");
			hbasedao.setValue(i+"技术创新和质量服务");
			hbasedao.execute("linlintest", null);
		}
		List<Map<String,Object>>  mapList1 = hbasedao.find("linlintest",null,null);
	 	//System.out.println("2");
		JsonUtil ju=new JsonUtil();
		String jsonresult=ju.list2json(mapList1);
		

		return jsonresult;
	}

}
