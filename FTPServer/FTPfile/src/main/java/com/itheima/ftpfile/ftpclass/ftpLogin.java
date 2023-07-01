package com.itheima.ftpfile.ftpclass;

import org.springframework.stereotype.Component;

@Component
public class ftpLogin {
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private  String hostname;
    private  int port;
    private  String username;
    private  String password;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private  String path;
}
