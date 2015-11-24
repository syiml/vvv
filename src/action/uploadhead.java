package action;

import java.io.*;
import util.Main;

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
    public static long maxHeadImgSize=Main.GV.getLong("maxHeadImgSize");

    public String upload() throws Exception{
        Main.debug("上传头像文件大小：" + upload.length());
        if(upload.length()>=maxHeadImgSize){
            return "toobig";
        }
        String path=Main.getRealPath("/");
        return Main.uploadFile(upload,path+"\\pic\\head\\"+Main.loginUser().getUsername()+".jpg");
    }
}
