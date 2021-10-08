package com.digital.login.dao;

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


import com.digital.login.entity.Login;
import com.digital.login.mapper.LoginMapper;
import com.digital.util.PageHelper;


@Repository("logindao")
public class LoginDao {
    @Autowired
	private LoginMapper LoginMapperImp;
	public int loginDaoFind(Login login) {
			int num = LoginMapperImp.getLogin(login);
			return num; 

	}
	public PageHelper.Page<Login>  loginDaoFindAll(int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		LoginMapperImp.selectLogin();
		 return PageHelper.endPage(); 

}
	

}
