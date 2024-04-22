package com.neusoft.mapper;

import com.neusoft.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface UserMapper {
	 @Select("#{sqlBuilder.buildSelectUserByIdByPass()}")
	 public User getUserByIdByPass(User user);
	 
	 @Select("#{sqlBuilder.buildSelectUserById()}")
	 public int getUserById(String userId);
	 
	 @Insert("#{sqlBuilder.buildInsertUser()}")
	 public int saveUser(User user);
}
