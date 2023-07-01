package com.itheima.ftpfile.controller;
import com.itheima.ftpfile.ftpclass.filedirectory;
import com.itheima.ftpfile.ftpclass.ftpLogin;
import com.itheima.ftpfile.ftpclass.ftpconnect;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FTPfileController {


    @RequestMapping(method = RequestMethod.POST, value = "/FTPUpload")
    public void FTPUpload(@RequestParam("file")List<MultipartFile> File, @RequestParam("Login") ftpLogin Login) throws ServletException, IOException {

        FTPClient ftp = ftpconnect.con(Login);
         if (File.size()<=0){
             System.out.println("没有文件请求");
         }
        for (MultipartFile item:File)
        {
            try {
                String fileName = new String(item.getOriginalFilename().getBytes("GBK"), "ISO-8859-1");
                InputStream input = item.getInputStream();
                ftp.storeFile(Login.getPath()+fileName, input);
                input.close();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        ftp.logout();
        ftp.disconnect();

    }


    @RequestMapping(method = RequestMethod.POST, value = "/FTPdownload")
    public void FTPdownload(HttpServletResponse response, @RequestBody ftpLogin Login) throws IOException {
        FTPClient ftp = ftpconnect.con(Login);
        String fileName = new String(Login.getPath().getBytes("GBK"), "ISO-8859-1");


        InputStream inputStream = ftp.retrieveFileStream(fileName);


        response.setContentType("application/x-download");

        // 写入响应输出流
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
        outputStream.close();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/FTPDelete")
    public void FTPDelete(@RequestBody ftpLogin Login) throws IOException {

        FTPClient ftp = ftpconnect.con(Login);
        // 删除目录
        String directoryToDelete = Login.getPath();
        boolean isDirectoryDeleted = ftp.removeDirectory(directoryToDelete);
        if (isDirectoryDeleted) {
            System.out.println("目录删除成功。");
        } else {
            System.out.println("目录删除失败。");
        }
        ftp.logout();
        ftp.disconnect();

    }

    @RequestMapping(method = RequestMethod.POST, value = "/FTPFilelist")
    public ArrayList<filedirectory> FTPFilelist(@RequestBody ftpLogin Login) throws IOException {

        ArrayList<filedirectory> filelist=new ArrayList<>();


        FTPClient ftp = ftpconnect.con(Login);

        List<FTPFile> ftplist = Arrays.stream(ftp.listFiles(Login.getPath())).toList();
        for (FTPFile file : ftplist)
        {
            filedirectory directory=new filedirectory();
            if (!file.isFile())
            {
                directory.setFilename(file.getName());
                directory.setFiletype(1);
                filelist.add(directory);
            }else
            {
                    directory.setFilename(file.getName());
                    directory.setFiletype(2);
                    filelist.add(directory);
            };
        }
        ftp.logout();
        ftp.disconnect();
        return filelist;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/FTPSearch")
    public String FTPSearch(@RequestBody ftpLogin Login) throws IOException {

        FTPClient ftp = ftpconnect.con(Login);
        ArrayList<String> childfile=new ArrayList<>();
        String filename=Login.getPath().substring(Login.getPath().lastIndexOf("/")+1);

        if (Login.getPath().substring(0,Login.getPath().indexOf("/")).contains("All"))
        {
            FTPFile[] files = ftp.listFiles("/All");
            for (FTPFile item:files)
            {
                    childfile.add(item.getName());
            }
        }
        else {
            FTPFile[] files = ftp.listFiles("/"+Login.getUsername());
            for (FTPFile item:files)
            {
                childfile.add(item.getName());
            }
        }
        ftp.logout();
        ftp.disconnect();
        for (String item:childfile)
        {
            if (item.contains(filename)){
                return  item;
            }
        }
        return "error";
    }


}


