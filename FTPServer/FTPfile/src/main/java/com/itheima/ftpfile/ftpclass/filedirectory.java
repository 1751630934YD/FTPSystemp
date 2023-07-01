package com.itheima.ftpfile.ftpclass;

import java.util.ArrayList;

public class filedirectory {


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFiletype() {
        return filetype;
    }

    public void setFiletype(int filetype) {
        this.filetype = filetype;
    }



    public ArrayList<filedirectory> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<filedirectory> children) {
        this.children = children;
    }
    String filename;
    int filetype;//1为文件夹 2为文件
    ArrayList<filedirectory> children;
}
