package com.digital.login.mapper;

import java.util.List;

import com.digital.login.entity.Login;

public interface LoginMapper {
	public int getLogin(Login login);
	public List<Login> selectLogin();


}
