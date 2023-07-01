package com.itheima.ftpfile.ftpclass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;

public class FtpLoginConverter implements Converter<String, ftpLogin> {
    @Override
    public ftpLogin convert(String source) {
        ftpLogin login=new ftpLogin();
        ObjectMapper mapper = new ObjectMapper();
        try {
            ftpLogin myObject = mapper.readValue(source, ftpLogin.class);
            // 为Goods实例赋值
            login.setHostname(myObject.getHostname());
            login.setUsername(myObject.getUsername());
            login.setPassword(myObject.getPassword());
            login.setPath(myObject.getPath());
            return login;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }




    }
}
