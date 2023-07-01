package com.itheima.ftpfile.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Loggermapper
{
     void AddLogger(String user,String  activity);
}
