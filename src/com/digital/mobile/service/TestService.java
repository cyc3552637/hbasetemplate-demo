package com.digital.mobile.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digital.mobile.dao.TestDao;
import com.digital.mobile.entity.TestEntity;
import com.digital.util.PageHelper.Page;



@Repository("testservice")
public class TestService {
	@Resource(name="testdao")
	TestDao testdao;
	public TestDao getTest() {
		return testdao;
	}
	public void setTest(TestDao testdao) {
		this.testdao = testdao;
	}
	@Transactional(rollbackFor=Exception.class)
	public Page testServiceFind(TestEntity testentity){
		return testdao.testDaoFind(1, 100, testentity);				
}
	@Transactional(rollbackFor=Exception.class)
	public int testServiceAdd(TestEntity testentity) {
       int num=testdao.testDaoAdd(testentity);
//     boolean flag1=testdao.testDaoAdd1(id,name);
	   return num;				
}
}
