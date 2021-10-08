package com.digital.mobile.mapper;

import java.util.List;

import com.digital.mobile.entity.TestEntity;
import com.digital.login.entity.Login;

public interface TestMapper {
	public List selectTest(TestEntity testentity);
	public int insertTest(TestEntity testentity);


}
