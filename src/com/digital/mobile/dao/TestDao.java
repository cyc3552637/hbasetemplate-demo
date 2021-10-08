package com.digital.mobile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;


import com.digital.mobile.entity.TestEntity;
import com.digital.mobile.mapper.TestMapper;
import com.digital.login.entity.Login;
import com.digital.login.mapper.LoginMapper;
import com.digital.util.PageHelper;


@Repository("testdao")
public class TestDao {
    @Autowired
	private TestMapper testMapperImpl;
    public PageHelper.Page<TestEntity> testDaoFind(int pageNum,int pageSize,TestEntity testentity) {
		PageHelper.startPage(pageNum, pageSize);
		testMapperImpl.selectTest(testentity);
		 return PageHelper.endPage(); 

	}
	public int  testDaoAdd(TestEntity testentity) {
		int num = 0;
		try{
				num =testMapperImpl.insertTest(testentity);
		}catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			
			return num;
		}

}
	

}
