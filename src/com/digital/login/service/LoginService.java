package com.digital.login.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digital.login.dao.LoginDao;
import com.digital.login.entity.Login;
import com.digital.util.PageHelper.Page;



@Repository("loginservice")
public class LoginService {
	@Resource(name="logindao")
	LoginDao logindao;
	public LoginDao getLogin() {
		return logindao;
	}
	public void setLogin(LoginDao logindao) {
		this.logindao = logindao;
	}

	@Transactional(rollbackFor=Exception.class)
	public int loginServiceFind(Login login){
		int num=logindao.loginDaoFind(login);
		return num;				
}
	@Transactional(rollbackFor=Exception.class)
	public Page loginServiceFindAll(int pageNum,int pageSize){
		Page page=logindao.loginDaoFindAll(pageNum, pageSize);
		return page;				
}
	@Cacheable(value="Login",key="'getLogin'")
	public Login loginRedis(int pageNum,int pageSize){
		Page page=logindao.loginDaoFindAll(pageNum, pageSize);
		List<Login> list=page.getResult();
		return  list.get(0);				
}
}
