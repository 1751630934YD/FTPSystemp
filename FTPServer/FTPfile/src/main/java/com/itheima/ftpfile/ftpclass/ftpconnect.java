package com.itheima.ftpfile.ftpclass;

//import com.itheima.ftpfile.Mybatis.SqlSessionconfig;
import com.itheima.ftpfile.mapper.usermapper;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Configuration
public class ftpconnect{


    public  static  FTPClient  con(ftpLogin ftp)
    {

        try {
        FTPClient ftpClient=new FTPClient();

            ftp.setPort(21);
            ftpClient.connect(ftp.getHostname(), ftp.getPort());
            ftpClient.login(ftp.getUsername(), ftp.getPassword());
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);

            return ftpClient;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}


