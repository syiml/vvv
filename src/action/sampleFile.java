package action;

import util.Main;
import util.FILE;
import util.Tool;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Syiml on 2015/10/21 0021.
 */
public class sampleFile  extends BaseAction{

    String pid;
    String filename;
    File samplein;
    String sampleinFileName;
    File sampleout;
    String sampleoutFileName;
    File spj;
    String spjFileName;
    private String fileName;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public File getSamplein() {
        return samplein;
    }

    public void setSamplein(File samplein) {
        this.samplein = samplein;
    }

    public String getSampleinFileName() {
        return sampleinFileName;
    }

    public void setSampleinFileName(String sampleinFileName) {
        this.sampleinFileName = sampleinFileName;
    }

    public File getSampleout() {
        return sampleout;
    }

    public void setSampleout(File sampleout) {
        this.sampleout = sampleout;
    }

    public String getSampleoutFileName() {
        return sampleoutFileName;
    }

    public void setSampleoutFileName(String sampleoutFileName) {
        this.sampleoutFileName = sampleoutFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getSpj() {

        return spj;
    }

    public void setSpj(File spj) {
        this.spj = spj;
    }

    public String getSpjFileName() {
        return spjFileName;
    }

    public void setSpjFileName(String spjFileName) {
        this.spjFileName = spjFileName;
    }

    public String del(){
        if(Main.loginUserPermission().getAddLocalProblem()){
            if(FILE.delFile(Integer.parseInt(pid),filename)){
                return "success";
            }else  return "error";
        }else{
            return "error";
        }
    }
    public String upload(){
        if(Main.loginUserPermission().getAddLocalProblem()){
            if(samplein!=null)FILE.uploadFile(Integer.parseInt(pid),sampleinFileName,samplein);
            if(sampleout!=null)FILE.uploadFile(Integer.parseInt(pid),sampleoutFileName,sampleout);
            if(spj!=null)FILE.uploadFile(Integer.parseInt(pid),"spj.cpp",spj);
            return "success";
        }else{
            return "error";
        }
    }
    public InputStream getDownloadFile(){
        //解解乱码
        //filename = new String(filename.getBytes("GBK"),"ISO-8859-1");
        //fileName=filename;
        //Tool.debug("DownLoad "+pid+","+filename);
        return FILE.download(Integer.parseInt(pid),filename);
    }
    public String download() throws Exception {
        if(Main.loginUserPermission().getAddLocalProblem())
            return "success";
        else return "error";
    }
}
