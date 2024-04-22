//建造者模式
/*使得 Mapper 接口中的 SQL 语句更加清晰和易于维护。*/

package com.neusoft.mapper;

import com.neusoft.po.User;

public class SqlBuilder {
    public static String buildSelectUserByIdByPass() {
        return "select * from user where userId=#{userId} and password=#{password}";
    }

    public static String buildSelectUserById() {
        return "select count(*) from user where userId=#{userId}";
    }

    public static String buildInsertUser() {
        return "insert into user values(#{userId},#{password},#{userName},#{userSex},null,1)";
    }
}
