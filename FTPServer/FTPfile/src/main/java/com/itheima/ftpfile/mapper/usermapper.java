package com.itheima.ftpfile.mapper;

import com.itheima.ftpfile.ftpclass.FTPuser;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface usermapper {

    FTPuser selectuser(String user,String password);

}
