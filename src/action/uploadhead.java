package action;

import java.io.*;
import Main.Main;

/**
 * Created by Syiml on 2015/9/28 0028.
 */
public class uploadhead {
    public File getUpload() {
        return upload;
    }
    public String getUploadFileName() {
        return uploadFileName;
    }
    public void setUpload(File upload) {
        this.upload = upload;
    }
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    private File upload;
    private String uploadFileName;

    public String upload() throws Exception{
        System.out.println(upload.length());
        if(upload.length()>512*1024){
            return "toobig";
        }
        String path=Main.getRealPath("/");
        return Main.uploadFile(upload,path+"\\pic\\head\\"+Main.loginUser().getUsername()+".jpg");
    }
}
